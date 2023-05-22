/*******************************************************************************
 * Copyright (c) 2028, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.impl.converters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A Map of PriorityConverters, that only stores the PriorityConverter with the highest priority for each Type
 */
public class PriorityConverterMap implements Map<Class<?>, PriorityConverter<?>> {

    private Map<Class<?>, PriorityConverter<?>> converters = new HashMap<>();
    private boolean unmodifiable = false;

    /**
     * Basic constructor
     */
    public PriorityConverterMap() {
        //no-op
    }

    /**
     * Copy constructor
     */
    public PriorityConverterMap(PriorityConverterMap toCopy) {
        for (PriorityConverter<?> converter : toCopy.getAll()) {
            _addConverter(converter);
        }
    }

    /**
     * Add a converter to the map if:
     * - there is no existing converter for that type
     * - there is an existing converter of equal priority
     * - there is an existing converter of lower priority
     *
     * @param converter the new converter
     * @return the new converter if it was added or the existing converter if it was not
     */
    public PriorityConverter<?> addConverter(PriorityConverter<?> converter) {
        PriorityConverter<?> existing = _addConverter(converter);

        return existing;
    }

    private PriorityConverter<?> _addConverter(PriorityConverter<?> converter) {
        if (this.unmodifiable) {
            throw new UnsupportedOperationException();
        }

        PriorityConverter<?> existing;
        Class<?> type = converter.getType();
        existing = converters.get(type);
        if (existing == null || existing.getPriority() <= converter.getPriority()) {
            converters.put(type, converter);
            existing = converter;
        }

        return existing;
    }

    /**
     * Add all of the converters from the given map to this one... if they have a higher priority as above
     *
     * @param convertersToAdd the converters to add
     */
    public void addAll(PriorityConverterMap convertersToAdd) {
        for (PriorityConverter<?> converter : convertersToAdd.converters.values()) {
            _addConverter(converter);
        }
    }

    /**
     * Get a converter for the given type
     *
     * @param <T>
     *
     * @param type the type to find a converter for
     * @return the converter for the given type
     */
    public PriorityConverter<?> getConverter(Type type) {
        PriorityConverter<?> converter = converters.get(type);
        return converter;
    }

    public void setUnmodifiable() {
        this.unmodifiable = true;
        this.converters = Collections.unmodifiableMap(this.converters);
    }

    /**
     * @param type
     * @return
     */
    public boolean hasType(Type type) {
        return converters.containsKey(type);
    }

    /**
     * @return
     */
    public Collection<PriorityConverter<?>> getAll() {
        return new ArrayList<PriorityConverter<?>>(converters.values());
    }

    /**
     * @return
     */
    public Collection<Class<?>> getTypes() {
        return new ArrayList<Class<?>>(converters.keySet());
    }

    @Override
    public String toString() {
        return "PriorityConverterMap:" + converters;
    }

    /** {@inheritDoc} */
    @Override
    public int size() {
        return this.converters.size();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEmpty() {
        return this.converters.isEmpty();
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsKey(Object key) {
        return this.converters.containsKey(key);
    }

    /** {@inheritDoc} */
    @Override
    public boolean containsValue(Object value) {
        return this.converters.containsValue(value);
    }

    /** {@inheritDoc} */
    @Override
    public PriorityConverter<?> get(Object key) {
        return this.converters.get(key);
    }

    /** {@inheritDoc} */
    @Override
    public PriorityConverter<?> put(Class<?> type, PriorityConverter<?> converter) {
        if (this.unmodifiable) {
            throw new UnsupportedOperationException();
        }

        PriorityConverter<?> existing;
        existing = this.converters.get(type);
        if (existing == null || existing.getPriority() <= converter.getPriority()) {
            existing = this.converters.put(type, converter);
        }

        return existing;
    }

    /** {@inheritDoc} */
    @Override
    public PriorityConverter<?> remove(Object key) {
        return this.converters.remove(key);
    }

    /** {@inheritDoc} */
    @Override
    public void putAll(Map<? extends Class<?>, ? extends PriorityConverter<?>> m) {
        for (Entry<? extends Class<?>, ? extends PriorityConverter<?>> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void clear() {
        this.converters.clear();
    }

    /** {@inheritDoc} */
    @Override
    public Set<Class<?>> keySet() {
        return this.converters.keySet();
    }

    /** {@inheritDoc} */
    @Override
    public Collection<PriorityConverter<?>> values() {
        return this.converters.values();
    }

    /** {@inheritDoc} */
    @Override
    public Set<Entry<Class<?>, PriorityConverter<?>>> entrySet() {
        return this.converters.entrySet();
    }
}