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

/**
 *
 */
public class StringConverter extends BuiltInConverter<String> {

    private static final long serialVersionUID = 8019214281322398345L;

    public StringConverter() {
        super(String.class);
    }

    /** {@inheritDoc} */
    @Override
    public String convert(String value) {
        return value;
    }

}