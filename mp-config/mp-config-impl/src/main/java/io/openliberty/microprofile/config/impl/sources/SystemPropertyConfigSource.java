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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 *
 */
public class SystemPropertyConfigSource implements ConfigSource {

    /** {@inheritDoc} */
    @Override
    public Set<String> getPropertyNames() {
        Set<Object> keys = System.getProperties().keySet();
        Set<String> propertyNames = new HashSet<>();
        keys.forEach((k) -> propertyNames.add((String) k));
        return propertyNames;
    }

    /** {@inheritDoc} */
    @Override
    public String getValue(String propertyName) {
        return System.getProperty(propertyName);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return "System Property ConfigSource";
    }

}
