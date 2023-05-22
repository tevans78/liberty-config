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

import java.util.OptionalDouble;

/**
 *
 */
public class OptionalDoubleConverter extends BuiltInConverter<OptionalDouble> {

    private static final long serialVersionUID = 7300932274562253116L;

    public OptionalDoubleConverter() {
        super(OptionalDouble.class);
    }

    /** {@inheritDoc} */
    @Override
    public OptionalDouble convert(String value) {
        if (value == null) {
            throw new NullPointerException("String value was null");
        }

        Double doubleValue = Double.valueOf(value);
        OptionalDouble optionalDouble = OptionalDouble.of(doubleValue);

        return optionalDouble;
    }

}
