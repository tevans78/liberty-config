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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 *
 */
public class EnvironmentConfigSource implements ConfigSource {

    public static final int ENVIRONMENT_VARIABLES_DEFAULT_ORDINAL = 300;
    private final Map<String, String> cache;
    private int ordinal;

    public EnvironmentConfigSource() {
        this.cache = new HashMap<>(System.getenv());

        String configOrdinal = getValue(CONFIG_ORDINAL);
        if (configOrdinal != null) {
            try {
                this.ordinal = Integer.parseInt(configOrdinal);
            } catch (NumberFormatException ignored) {
                this.ordinal = ENVIRONMENT_VARIABLES_DEFAULT_ORDINAL;
            }
        } else {
            this.ordinal = ENVIRONMENT_VARIABLES_DEFAULT_ORDINAL;
        }
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getPropertyNames() {
        Set<String> keys = System.getenv().keySet();
        return keys;
    }

    /** {@inheritDoc} */
    @Override
    public String getValue(String propertyName) {
        String value = this.cache.get(propertyName);
        if (value == null) {
            String altName = propertyName.replaceAll("[^a-zA-Z0-9_]", "_");
            value = this.cache.get(altName);

            if (value == null) {
                altName = altName.toUpperCase();
                value = this.cache.get(altName);
            }
        }

        return value;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return "Environment Variable ConfigSource";
    }

    @Override
    public int getOrdinal() {
        return this.ordinal;
    }

}
