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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import io.openliberty.microprofile.config.impl.converters.StringConverter;
import io.openliberty.microprofile.config.impl.sources.EnvironmentConfigSource;
import io.openliberty.microprofile.config.impl.sources.SystemPropertyConfigSource;

/**
 *
 */
public class ConfigBuilderImpl implements ConfigBuilder {

    private boolean addDefaultSources = false;
    private boolean addDiscoveredSources = false;
    private boolean addDiscoveredConverters = false;
    private ClassLoader loader = Thread.currentThread().getContextClassLoader();
    private final Map<Class<?>, PriorityConverter<?>> priorityConverters = new HashMap<>();
    private final List<ConfigSource> configSources = new ArrayList<>();
    private List<ConfigSource> defaultSources;
    private Map<Class<?>, Converter<?>> defaultConverters;

    /** {@inheritDoc} */
    @Override
    public ConfigBuilder addDefaultSources() {
        this.addDefaultSources = true;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ConfigBuilder addDiscoveredSources() {
        this.addDiscoveredSources = true;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ConfigBuilder addDiscoveredConverters() {
        this.addDiscoveredConverters = true;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ConfigBuilder forClassLoader(ClassLoader loader) {
        this.loader = loader;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ConfigBuilder withSources(ConfigSource... sources) {
        for (ConfigSource source : sources) {
            this.configSources.add(source);
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ConfigBuilder withConverters(Converter<?>... converters) {
        for (Converter<?> converter : converters) {
            withConverter(converter);
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public <T> ConfigBuilder withConverter(Class<T> type, int priority, Converter<T> converter) {
        PriorityConverter<T> priorityConverter = new PriorityConverter<T>(type, priority, converter);
        withConverter(priorityConverter);
        return this;
    }

    private <T> ConfigBuilder withConverter(Converter<T> converter) {
        withConverter(PriorityConverter.eraseConverterType(converter), PriorityConverter.DEFAULT_CONVERTER_PRIORITY, converter);
        return this;
    }

    private <T> ConfigBuilder withConverter(PriorityConverter<T> converter) {
        Class<T> type = converter.getType();
        PriorityConverter<T> existing = (PriorityConverter<T>) this.priorityConverters.get(type);
        if (existing == null || existing.getPriority() < converter.getPriority()) {
            this.priorityConverters.put(type, converter);
        }
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Config build() {
        List<ConfigSource> configSources = finalizeConfigSources(this.loader);
        Map<Class<?>, Converter<?>> converters = finalizeConverters(this.loader);
        return new ConfigImpl(configSources, converters);
    }

    private Map<Class<?>, Converter<?>> finalizeConverters(ClassLoader loader) {
        Map<Class<?>, Converter<?>> finalConverters = new HashMap<>();
        finalConverters.putAll(getDefaultConverters());
        //TODO discover converters via loader
        return finalConverters;
    }

    /**
     * @return
     */
    private Map<Class<?>, Converter<?>> getDefaultConverters() {
        if (this.defaultConverters == null) {
            defaultConverters = new HashMap<>();
            defaultConverters.put(String.class, new StringConverter());
            //TODO add the others
        }
        return defaultConverters;
    }

    private List<ConfigSource> finalizeConfigSources(ClassLoader loader) {
        List<ConfigSource> finalSources = new ArrayList<>();
        if (this.addDefaultSources) {
            finalSources.addAll(getDefaultSources());
        }
        Collections.sort(finalSources, ConfigSourceComparator.INSTANCE);
        //TODO discover sources via loader
        return finalSources;
    }

    /**
     * @return
     */
    private List<ConfigSource> getDefaultSources() {
        if (this.defaultSources == null) {
            defaultSources = new ArrayList<>();
            defaultSources.add(new SystemPropertyConfigSource());
            defaultSources.add(new EnvironmentConfigSource());
            //TODO add the others
        }
        return defaultSources;
    }

}
