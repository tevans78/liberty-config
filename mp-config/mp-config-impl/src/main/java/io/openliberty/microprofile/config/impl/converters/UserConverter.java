/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.impl.converters;

import java.lang.reflect.TypeVariable;

import org.eclipse.microprofile.config.spi.Converter;

import io.openliberty.microprofile.config.impl.utils.Erasure;
import jakarta.annotation.Priority;

/**
 *
 */
public class UserConverter<T> extends PriorityConverter<T> {

    private static final long serialVersionUID = 6623391401751220380L;
    public static final int DEFAULT_CONVERTER_PRIORITY = 100;
    private final Converter<T> converter;

    public static <X> UserConverter<X> newInstance(Converter<X> converter) {
        Priority priorityAnno = converter.getClass().getAnnotation(Priority.class);
        int priority = DEFAULT_CONVERTER_PRIORITY;
        if (priorityAnno != null) {
            priority = priorityAnno.value();
        }
        return new UserConverter<>(priority, converter);
    }

    public static <X> UserConverter<X> newInstance(int priority, Converter<X> converter) {
        return new UserConverter<>(priority, converter);
    }

    public UserConverter(int priority, Converter<T> converter) {
        this(eraseConverterType(converter), priority, converter);
    }

    public UserConverter(Class<T> type, int priority, Converter<T> converter) {
        super(type, priority);
        this.converter = converter;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> eraseConverterType(Converter<T> converter) {
        TypeVariable<?>[] typeVariables = converter.getClass().getTypeParameters();
        TypeVariable<?> typeVariable = typeVariables[0];
        Class<T> clazz = (Class<T>) Erasure.erase(typeVariable);
        return clazz;
    }

    /** {@inheritDoc} */
    @Override
    public T convert(String value) throws IllegalArgumentException, NullPointerException {
        return converter.convert(value);
    }

}
