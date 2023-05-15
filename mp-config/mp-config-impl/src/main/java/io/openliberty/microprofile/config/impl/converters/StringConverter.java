/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.impl.converters;

import org.eclipse.microprofile.config.spi.Converter;

/**
 *
 */
public class StringConverter implements Converter<String> {

    /** {@inheritDoc} */
    @Override
    public String convert(String value) throws IllegalArgumentException, NullPointerException {
        return value;
    }

}
