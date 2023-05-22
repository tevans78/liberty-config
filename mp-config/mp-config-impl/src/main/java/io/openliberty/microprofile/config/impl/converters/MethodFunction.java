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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Function;

import io.openliberty.microprofile.config.impl.exceptions.ConversionException;

public class MethodFunction<X> implements Function<String, X> {
    private final Method method;

    protected MethodFunction(Method method) {
        this.method = method;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public X apply(String value) {
        X converted = null;
        if (value != null) { //if the value is null then we always return null
            try {
                converted = (X) method.invoke(null, value);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof IllegalArgumentException) {
                    throw (IllegalArgumentException) cause;
                } else {
                    throw new ConversionException(cause);
                }
            } catch (IllegalAccessException e) {
                throw new ConversionException(e);
            }
        }
        return converted;
    }

    public static <X> Function<String, X> getValueOfFunction(Class<X> reflectionClass) {
        return getFunction(reflectionClass, "valueOf", String.class);
    }

    public static <X> Function<String, X> getParseFunction(Class<X> reflectionClass) {
        return getFunction(reflectionClass, "parse", CharSequence.class);
    }

    public static <X> Function<String, X> getOfMethod(Class<X> reflectionClass) {
        return getFunction(reflectionClass, "of", String.class);
    }

    public static <X> Function<String, X> getFunction(Class<X> reflectionClass, String methodName, Class<?>... paramTypes) {
        Function<String, X> implicitFunction = null;
        try {
            Method method = reflectionClass.getMethod(methodName, paramTypes);
            if ((method.getModifiers() & Modifier.STATIC) == 0) {
                method = null;
            } else if (!reflectionClass.equals(method.getReturnType())) {
                method = null;
            }

            if (method != null) {
                implicitFunction = new MethodFunction<X>(method);
            }
        } catch (NoSuchMethodException e) {
            //No FFDC
        }
        return implicitFunction;
    }
}