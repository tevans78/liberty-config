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

import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 *
 */
public class EnvironmentConfigSource implements ConfigSource {

    /** {@inheritDoc} */
    @Override
    public Set<String> getPropertyNames() {
        Set<String> keys = System.getenv().keySet();
        return keys;
    }

    /** {@inheritDoc} */
    @Override
    public String getValue(String propertyName) {
        return System.getenv(propertyName);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return "Environment Variable ConfigSource";
    }

}
