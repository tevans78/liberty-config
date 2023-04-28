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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jakarta.config.Loader;

/**
 * Unit test for Jakarta Config Loader.
 */
public class LoaderTest {

    @Test
    public void testBootstrap() {
        Loader loader = Loader.bootstrap();
        assertNotNull(loader);
        assertTrue(loader instanceof LoaderImpl);
    }

    @Test
    public void testConfigObject() {
        String valueA = "valueA";
        String valueB = "valueB";
        System.setProperty("propertyA", valueA);
        System.setProperty("propertyB", valueB);
        Loader loader = Loader.bootstrap();
        MyConfig myConfig = loader.load(MyConfig.class);
        assertNotNull(myConfig);

        String propertyA = myConfig.getPropertyA();
        assertEquals(valueA, propertyA);

        String propertyB = myConfig.getPropertyB();
        assertEquals(valueB, propertyB);
    }
}
