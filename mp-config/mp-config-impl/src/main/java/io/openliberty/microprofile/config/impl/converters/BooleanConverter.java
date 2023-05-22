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
 *
 */
public class BooleanConverter extends BuiltInConverter<Boolean> {

    private static final long serialVersionUID = -6098113701355832413L;

    public BooleanConverter() {
        super(Boolean.class);
    }

    /** {@inheritDoc} */
    @Override
    public Boolean convert(String v) {
        if (v == null) {
            return null;
        }
        if (v.equalsIgnoreCase("true") || v.equalsIgnoreCase("yes") || v.equalsIgnoreCase("y") || v.equalsIgnoreCase("on") || v.equalsIgnoreCase("1")) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
