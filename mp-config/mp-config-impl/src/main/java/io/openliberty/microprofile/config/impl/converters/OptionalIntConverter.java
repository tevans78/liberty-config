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

import java.util.OptionalInt;

/**
 *
 */
public class OptionalIntConverter extends BuiltInConverter<OptionalInt> {

    private static final long serialVersionUID = 918300308667863592L;

    public OptionalIntConverter() {
        super(OptionalInt.class);
    }

    /** {@inheritDoc} */
    @Override
    public OptionalInt convert(String value) {
        if (value == null) {
            throw new NullPointerException("String value was null");
        }

        Integer intValue = Integer.valueOf(value);
        OptionalInt optionalInt = OptionalInt.of(intValue);

        return optionalInt;
    }

}
