/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.impl.sources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.openliberty.microprofile.config.impl.exceptions.ConfigException;

/**
 *
 */
public class PropertiesFileConfigSource implements ConfigSource {

    private final Properties properties;

    public PropertiesFileConfigSource(URL fileURL) {
        this.properties = new Properties();
        try (InputStream is = fileURL.openStream()) {
            this.properties.load(is);
        } catch (IOException e) {
            throw new ConfigException(e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getPropertyNames() {
        return this.properties.stringPropertyNames();
    }

    /** {@inheritDoc} */
    @Override
    public String getValue(String propertyName) {
        return this.properties.getProperty(propertyName);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return "MicroProfile Config Properties File ConfigSource";
    }

}
