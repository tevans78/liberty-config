/*******************************************************************************
 * Copyright (c) 2019, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.impl.converters;

public class CharacterConverter extends BuiltInConverter<Character> {

    private static final long serialVersionUID = -8355509709773152063L;

    public CharacterConverter() {
        super(Character.class);
    }

    /** {@inheritDoc} */
    @Override
    public Character convert(String v) {
        if (v == null) {
            return null;
        }
        if (v.length() != 1) {
            throw new IllegalArgumentException("Not a valid character: " + v);
        } else {
            return v.charAt(0);
        }
    }
}
