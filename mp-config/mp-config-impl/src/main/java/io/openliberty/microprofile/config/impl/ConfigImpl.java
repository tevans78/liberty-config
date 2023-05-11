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

import java.util.Optional;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;

/**
 *
 */
public class ConfigImpl implements Config {

    /** {@inheritDoc} */
    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        // TODO Auto-generated method stub
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public ConfigValue getConfigValue(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        // TODO Auto-generated method stub
        return Optional.empty();
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
