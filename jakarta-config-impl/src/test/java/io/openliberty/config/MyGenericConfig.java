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
public class MyGenericConfig<T> {

    private T propertyA;
    private T propertyB;

    public T getPropertyA() {
        return propertyA;
    }

    public T getPropertyB() {
        return propertyB;
    }
}
