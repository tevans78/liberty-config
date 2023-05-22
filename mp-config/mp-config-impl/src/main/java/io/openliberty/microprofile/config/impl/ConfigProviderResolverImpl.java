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

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

/**
 *
 */
public class ConfigProviderResolverImpl extends ConfigProviderResolver {

    /** {@inheritDoc} */
    @Override
    public Config getConfig() {
        Config config = getConfig(Thread.currentThread().getContextClassLoader());
        return config;
    }

    /** {@inheritDoc} */
    @Override
    public Config getConfig(ClassLoader loader) {
        ConfigBuilder builder = getBuilder();
        builder.forClassLoader(loader);
        builder.addDefaultSources();
        builder.addDiscoveredSources();
        builder.addDiscoveredConverters();
        Config config = builder.build(); //TODO cache config by loader
        return config;
    }

    /** {@inheritDoc} */
    @Override
    public ConfigBuilder getBuilder() {
        return new ConfigBuilderImpl();
    }

    /** {@inheritDoc} */
    @Override
    public void registerConfig(Config config, ClassLoader classLoader) {
        // TODO Auto-generated method stub

    }

    /** {@inheritDoc} */
    @Override
    public void releaseConfig(Config config) {
        // TODO Auto-generated method stub

    }

}
