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

import java.util.Comparator;

import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 *
 */
public class ConfigSourceComparator implements Comparator<ConfigSource> {

    public static final ConfigSourceComparator INSTANCE = new ConfigSourceComparator();

    /** {@inheritDoc} */
    @Override
    public int compare(ConfigSource o1, ConfigSource o2) {
        //if the ordinal of o2 is higher than o1, return a positive integer
        //if the ordinal of o2 is lower than o1, return a negative integer
        //if equal, return zero
        return o2.getOrdinal() - o1.getOrdinal();
    }

}
