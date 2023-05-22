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
 * A BuiltInConverter has a fixed priority of 1
 */
public abstract class BuiltInConverter<T> extends PriorityConverter<T> {

    private static final long serialVersionUID = 3763061786117675188L;
    //Priority for BUILT IN Converters
    public static final int BUILTIN_CONVERTER_PRIORITY = 1;

    /**
     * Construct a new PriorityConverter using explicit type and priority values
     *
     * @param type The type to convert to
     * @param converter The actual converter
     */
    public BuiltInConverter(Class<T> type) {
        super(type, BUILTIN_CONVERTER_PRIORITY);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Built In Converter for type " + getType();
    }

}
