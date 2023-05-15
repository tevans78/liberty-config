/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.impl;

import java.lang.reflect.TypeVariable;

import org.eclipse.microprofile.config.spi.Converter;

import io.openliberty.microprofile.config.impl.utils.Erasure;

/**
 *
 */
public class PriorityConverter<T> {

    public static final int DEFAULT_CONVERTER_PRIORITY = 100;
    private final Converter<T> converter;
    private final int priority;
    private Class<T> type;

    public static <X> PriorityConverter<X> newInstance(int priority, Converter<X> converter) {
        return new PriorityConverter<>(priority, converter);
    }

    public PriorityConverter(int priority, Converter<T> converter) {
        this(eraseConverterType(converter), priority, converter);
    }

    public PriorityConverter(Class<T> type, int priority, Converter<T> converter) {
        this.converter = converter;
        this.priority = priority;
        this.type = type;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return
     */
    public Class<T> getType() {
        return this.type;
    }

    public static <T> Class<T> eraseConverterType(Converter<T> converter) {
        TypeVariable<?>[] typeVariables = converter.getClass().getTypeParameters();
        TypeVariable<?> typeVariable = typeVariables[0];
        Class<T> clazz = (Class<T>) Erasure.erase(typeVariable);
        return clazz;
    }

}
