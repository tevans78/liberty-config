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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

/**
 *
 */
public class ConfigImpl implements Config {

    private final List<ConfigSource> configSources;
    private final Map<Class<?>, ? extends Converter<?>> converters;

    /**
     * @param configSources
     * @param converters2
     */
    public ConfigImpl(List<ConfigSource> configSources, Map<Class<?>, ? extends Converter<?>> converters) {
        this.configSources = configSources;
        this.converters = converters;
    }

    /** {@inheritDoc} */
    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        Optional<T> optionalValue = getOptionalValue(propertyName, propertyType);
        T value = optionalValue.get();
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public ConfigValue getConfigValue(String propertyName) {
        ConfigValue configValue = null;
        for (ConfigSource source : configSources) {
            String rawValue = source.getValue(propertyName);
            if (rawValue != null) {
                String value = rawValue; //TODO apply transformations/expand variables
                String sourceName = source.getName();
                int sourceOrdinal = source.getOrdinal();
                configValue = new ConfigValueImpl(propertyName, value, rawValue, sourceName, sourceOrdinal);
                break;
            }
        }
        if (configValue == null) {
            configValue = new ConfigValueImpl(propertyName, null, null, null, 0);
        }
        return configValue;
    }

    /** {@inheritDoc} */
    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        Optional<T> value = null;
        ConfigValue configValue = getConfigValue(propertyName);
        if (configValue != null) {
            String stringValue = configValue.getValue();
            if (stringValue == null) {
                value = Optional.empty();
            } else {
                T convertedValue = (T) stringValue; //TODO apply converters
                value = Optional.of(convertedValue);
            }
        } else {
            value = Optional.empty();
        }
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public Iterable<String> getPropertyNames() {
        // TODO Auto-generated method stub
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Iterable<ConfigSource> getConfigSources() {
        // TODO Auto-generated method stub
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    /** {@inheritDoc} */
    @Override
    public <T> T unwrap(Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

}
