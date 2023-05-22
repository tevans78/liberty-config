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

import java.util.OptionalLong;

/**
 *
 */
public class OptionalLongConverter extends BuiltInConverter<OptionalLong> {

    private static final long serialVersionUID = 1718702805391557912L;

    public OptionalLongConverter() {
        super(OptionalLong.class);
    }

    /** {@inheritDoc} */
    @Override
    public OptionalLong convert(String value) {
        if (value == null) {
            throw new NullPointerException("String value was null");
        }

        Long longValue = Long.valueOf(value);
        OptionalLong optionalLong = OptionalLong.of(longValue);

        return optionalLong;
    }

}
