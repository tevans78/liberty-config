/*******************************************************************************
 * Copyright (c) 2018, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package io.openliberty.microprofile.config.impl.converters;

import java.util.function.Function;

import io.openliberty.microprofile.config.impl.exceptions.ConversionException;

/**
 *
 */
public class ImplicitConverter<T> extends BuiltInConverter<T> {

    private static final long serialVersionUID = -396884586129849319L;
    private final Function<String, T> implicitFunction;

    /**
     *
     * @param converterType The class to convert using
     */
    public ImplicitConverter(Class<T> converterType) {
        super(converterType);
        this.implicitFunction = getImplicitFunction(converterType);
    }

    /**
     * <p>If no explicit Converter and no built-in Converter could be found for a certain type,
     * the {@code Config} provides an <em>Implicit Converter</em>, if</p>
     * <ul>
     * <li>the target type {@code T} has a {@code public static T of(String)} method, or</li>
     * <li>the target type {@code T} has a {@code public static T valueOf(String)} method, or</li>
     * <li>The target type {@code T} has a public Constructor with a String parameter, or</li>
     * <li>the target type {@code T} has a {@code public static T parse(CharSequence)} method</li>
     * </ul>
     *
     * @param converterType The class to convert using
     */
    protected <X> Function<String, X> getImplicitFunction(Class<X> converterType) {
        Function<String, X> implicitFunction = null;

        implicitFunction = MethodFunction.getOfMethod(converterType);

        if (implicitFunction == null) {
            implicitFunction = MethodFunction.getValueOfFunction(converterType);
        }

        if (implicitFunction == null) {
            implicitFunction = ConstructorFunction.getConstructorFunction(converterType);
        }

        if (implicitFunction == null) {
            implicitFunction = MethodFunction.getParseFunction(converterType);
        }
        if (implicitFunction == null) {
            throw new IllegalArgumentException("implicit string constructor method not found"); //TODO NLS
        }

        return implicitFunction;
    }

    /** {@inheritDoc} */
    @Override
    public T convert(String value) {
        try {
            return this.implicitFunction.apply(value);
        } catch (ConversionException e) { //The Config 1.3 spec clarified that the convert method should throw IllegalArgumentException
                                          //if the value cannot be converted to the specified type
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) cause;
            } else {
                throw new IllegalArgumentException(cause);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Implicit Converter for type " + getType();
    }
}
