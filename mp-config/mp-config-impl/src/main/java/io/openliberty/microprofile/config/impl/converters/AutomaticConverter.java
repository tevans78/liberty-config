/*******************************************************************************
 * Copyright (c) 2018, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.impl.converters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import io.openliberty.microprofile.config.impl.exceptions.ConversionException;

/**
 *
 */
public class AutomaticConverter<T> extends BuiltInConverter<T> {

    private static final long serialVersionUID = -3394699675505362327L;
    private final Method valueOfMethod;
    private Constructor<?> ctor;
    private Method parseMethod;

    /**
     *
     * @param converterType The class to convert using
     */
    public AutomaticConverter(Class<T> converterType) {
        super(converterType);

        //in version 1.1 we always look for valueOf before a String constructor
        this.valueOfMethod = getValueOfMethod(converterType);
        if (this.valueOfMethod == null) {
            this.ctor = getConstructor(converterType);
        }

        if (this.ctor == null && this.valueOfMethod == null) {
            this.parseMethod = getParse(converterType);
        }

        if (this.ctor == null && this.valueOfMethod == null && this.parseMethod == null) {
            throw new IllegalArgumentException("Implicit String constructor not found");//TODO NLS
        } else {
//            if (TraceComponent.isAnyTracingEnabled() && tc.isDebugEnabled()) {
//                if (this.ctor != null) {
//                    Tr.debug(tc, "Automatic converter for {0} using {1}", converterType, this.ctor);
//                } else if (this.valueOfMethod != null) {
//                    Tr.debug(tc, "Automatic converter for {0} using {1}", converterType, this.valueOfMethod);
//                } else if (this.parseMethod != null) {
//                    Tr.debug(tc, "Automatic converter for {0} using {1}", converterType, this.parseMethod);
//                }
//            }
        }
    }

    private static <M> Constructor<M> getConstructor(Class<M> reflectionClass) {
        Constructor<M> ctor = null;
        try {
            ctor = reflectionClass.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            //No FFDC
        }
        return ctor;
    }

    private static Method getValueOfMethod(Class<?> reflectionClass) {
        Method method = null;
        try {
            method = reflectionClass.getMethod("valueOf", String.class);
            if ((method.getModifiers() & Modifier.STATIC) == 0) {
                method = null;
            } else if (!reflectionClass.equals(method.getReturnType())) {
                method = null;
            }
        } catch (NoSuchMethodException e) {
            //No FFDC
        }

        return method;
    }

    private static Method getParse(Class<?> reflectionClass) {
        Method method = null;
        try {
            method = reflectionClass.getMethod("parse", CharSequence.class);
            if ((method.getModifiers() & Modifier.STATIC) == 0) {
                method = null;
            } else if (!reflectionClass.equals(method.getReturnType())) {
                method = null;
            }
        } catch (NoSuchMethodException e) {
            //No FFDC
        }
        return method;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public T convert(String value) {
        T converted = null;
        if (value != null) { //if the value is null then we always return null
            try {
                //in version 1.1 we always look for valueOf before a String constructor
                if (this.valueOfMethod != null) {
                    converted = (T) this.valueOfMethod.invoke(null, value);
                } else if (this.ctor != null) {
                    converted = (T) this.ctor.newInstance(value);
                } else if (this.parseMethod != null) {
                    converted = (T) this.parseMethod.invoke(null, value);
                }
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof IllegalArgumentException) {
                    throw (IllegalArgumentException) cause;
                } else {
                    throw new ConversionException(cause);
                }
            } catch (IllegalAccessException | InstantiationException e) {
                throw new ConversionException(e);
            }
        }
        return converted;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Automatic Converter for type " + getType();
    }
}
