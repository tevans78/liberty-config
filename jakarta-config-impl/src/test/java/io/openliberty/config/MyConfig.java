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

import jakarta.config.Configuration;

@Configuration
public class MyConfig {

    private String propertyA;
    private String propertyB;
    //private List<MyConvertibleType> propertyC;

    public String getPropertyA() {
        return propertyA;
    }

    public String getPropertyB() {
        return propertyB;
    }

//    public List<MyConvertibleType> getPropertyC() {
//        return propertyC;
//    }
}
