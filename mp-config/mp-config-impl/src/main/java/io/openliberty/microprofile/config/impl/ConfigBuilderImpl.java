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
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

import io.openliberty.microprofile.config.impl.converters.DefaultConverters;
import io.openliberty.microprofile.config.impl.converters.PriorityConverter;
import io.openliberty.microprofile.config.impl.converters.PriorityConverterMap;
import io.openliberty.microprofile.config.impl.converters.UserConverter;
import io.openliberty.microprofile.config.impl.sources.DefaultSources;

/**
 *
 */
public class ConfigBuilderImpl implements ConfigBuilder {

    private boolean addDefaultSources = false;
    private boolean addDiscoveredSources = false;
    private boolean addDiscoveredConverters = false;
    private ClassLoader loader = Thread.currentThread().getContextClassLoader();
    private final PriorityConverterMap priorityConverters = new PriorityConverterMap();
    private final List<ConfigSource> configSources = new ArrayList<>();

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
        UserConverter<T> priorityConverter = new UserConverter<T>(type, priority, converter);
        this.priorityConverters.addConverter(priorityConverter);
        return this;
    }

    private <T> ConfigBuilder withConverter(Converter<T> converter) {
        withConverter(UserConverter.eraseConverterType(converter), PriorityConverter.DEFAULT_CONVERTER_PRIORITY, converter);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public Config build() {
        List<ConfigSource> configSources = finalizeConfigSources(this.loader);
        Map<Class<?>, ? extends Converter<?>> converters = finalizeConverters(this.loader);
        return new ConfigImpl(configSources, converters);
    }

    private Map<Class<?>, ? extends Converter<?>> finalizeConverters(ClassLoader loader) {
        PriorityConverterMap finalConverters = new PriorityConverterMap();
        finalConverters.addAll(DefaultConverters.getDefaultConverters());
        if (this.addDiscoveredConverters) {
            finalConverters.addAll(DefaultConverters.getDiscoveredConverters(loader));
        }

        return finalConverters;
    }

    private List<ConfigSource> finalizeConfigSources(ClassLoader loader) {
        List<ConfigSource> finalSources = new ArrayList<>();
        if (this.addDefaultSources) {
            finalSources.addAll(DefaultSources.getDefaultSources(loader));
        }
        if (this.addDiscoveredSources) {
            finalSources.addAll(DefaultSources.getDiscoveredSources(loader));
        }

        Collections.sort(finalSources, ConfigSourceComparator.INSTANCE);

        for (ConfigSource source : finalSources) {
            System.out.println(source.getOrdinal() + ": " + source.getName());
        }

        return finalSources;
    }

}
