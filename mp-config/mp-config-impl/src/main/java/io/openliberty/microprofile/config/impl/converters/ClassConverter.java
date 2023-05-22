/*******************************************************************************
 * Copyright (c) 2018, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.impl.converters;

/**
 * Convert from the String name of a class to an actual Class, as loaded by Class.forName
 */
@SuppressWarnings("rawtypes")
public class ClassConverter extends BuiltInConverter<Class> {

    private static final long serialVersionUID = 6461766441390853L;

    public ClassConverter() {
        super(Class.class);
    }

    /** {@inheritDoc} */
    @Override
    public Class<?> convert(String rawString) {
        Class<?> converted = null;
        if (rawString != null) {
            try {
                converted = Class.forName(rawString);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return converted;
    }

}
