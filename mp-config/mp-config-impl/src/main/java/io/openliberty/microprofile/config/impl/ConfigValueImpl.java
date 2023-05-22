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

import org.eclipse.microprofile.config.ConfigValue;

/**
 *
 */
public class ConfigValueImpl implements ConfigValue {

    private final String propertyName;
    private final String value;
    private final String rawValue;
    private final String sourceName;
    private final int sourceOrdinal;

    public ConfigValueImpl(String propertyName, String value, String rawValue, String sourceName, int sourceOrdinal) {
        this.propertyName = propertyName;
        this.value = value;
        this.rawValue = rawValue;
        this.sourceName = sourceName;
        this.sourceOrdinal = sourceOrdinal;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return this.propertyName;
    }

    /** {@inheritDoc} */
    @Override
    public String getValue() {
        return this.value;
    }

    /** {@inheritDoc} */
    @Override
    public String getRawValue() {
        return this.rawValue;
    }

    /** {@inheritDoc} */
    @Override
    public String getSourceName() {
        return this.sourceName;
    }

    /** {@inheritDoc} */
    @Override
    public int getSourceOrdinal() {
        return this.sourceOrdinal;
    }

}
