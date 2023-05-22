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
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.openliberty.microprofile.config.impl.exceptions.ConfigException;

/**
 *
 */
public class DefaultSources {
    /**
     * @return
     */
    public static List<ConfigSource> getDefaultSources(ClassLoader loader) {
        List<ConfigSource> defaultSources = new ArrayList<>();
        defaultSources.add(new SystemPropertyConfigSource());
        defaultSources.add(new EnvironmentConfigSource());

        defaultSources.addAll(getPropertiesFileConfigSources(loader));

        return defaultSources;
    }

    /**
     * @return
     */
    private static List<ConfigSource> getPropertiesFileConfigSources(ClassLoader loader) {
        List<ConfigSource> sources = new ArrayList<>();

        try {
            Enumeration<URL> urls = loader.getResources("META-INF/microprofile-config.properties");
            Iterator<URL> itr = urls.asIterator();
            while (itr.hasNext()) {
                URL url = itr.next();
                PropertiesFileConfigSource source = new PropertiesFileConfigSource(url);
                sources.add(source);
            }
        } catch (IOException e) {
            throw new ConfigException(e);
        }

        return sources;
    }

    /**
     * @return discoveredSources
     */
    public static List<ConfigSource> getDiscoveredSources(ClassLoader classLoader) {
        List<ConfigSource> discoveredSources = new ArrayList<>();

        //load config sources using the service loader
        try {
            ServiceLoader<ConfigSource> sl = ServiceLoader.load(ConfigSource.class, classLoader);
            for (ConfigSource source : sl) {
                discoveredSources.add(source);
            }
        } catch (ServiceConfigurationError e) {
            throw new ConfigException("Unable to discover sources", e);
        }

        return discoveredSources;
    }
}
