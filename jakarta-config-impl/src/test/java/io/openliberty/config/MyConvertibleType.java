/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.config;

public class MyConvertibleType {

    private final String value;

    /**
     * @param stringValue
     */
    public MyConvertibleType(String stringValue) {
        this.value = stringValue;
    }

    public static final MyConvertibleType valueOf(String stringValue) {
        return new MyConvertibleType(stringValue);
    }

    @Override
    public String toString() {
        return "MyConvertibleType: " + value;
    }
}
