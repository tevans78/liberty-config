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
import java.util.function.Function;

import io.openliberty.microprofile.config.impl.exceptions.ConversionException;

public class ConstructorFunction<X> implements Function<String, X> {
    private final Constructor<X> constructor;

    public ConstructorFunction(Constructor<X> constructor) {
        this.constructor = constructor;
    }

    /** {@inheritDoc} */
    @Override
    public X apply(String value) {
        X converted = null;
        if (value != null) { //if the value is null then we always return null
            try {
                converted = constructor.newInstance(value);
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

    public static <X> Function<String, X> getConstructorFunction(Class<X> reflectionClass) {
        Function<String, X> implicitFunction = null;
        try {
            Constructor<X> ctor = reflectionClass.getConstructor(String.class);
            implicitFunction = new ConstructorFunction<X>(ctor);
        } catch (NoSuchMethodException e) {
            //No FFDC
        }

        return implicitFunction;
    }

}