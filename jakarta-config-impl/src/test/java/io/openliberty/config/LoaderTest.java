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

import org.junit.Ignore;
import org.junit.Test;

import jakarta.config.Loader;
import jakarta.config.NoSuchObjectException;
import jakarta.config.TypeToken;

/**
 * Unit test for Jakarta Config Loader.
 */
public class LoaderTest {

    private static final String PROPERTY_A = "propertyA";
    private static final String PROPERTY_B = "propertyB";
    private static final String PROPERTY_C = "propertyC";
    private static final String VALUE_A = "valueA";
    private static final String VALUE_B = "valueB";
    private static final String VALUE_C1 = "valueC1";
    private static final String VALUE_C2 = "valueC2";
    private static final String VALUE_C3 = "valueC3";

    @Test
    public void testBootstrap() {
        Loader loader = Loader.bootstrap();
        assertNotNull(loader);
        assertTrue(loader instanceof LoaderImpl);
    }

    @Test
    public void testConfigObject() {
        Loader loader = setup();
        MyConfig myConfig = loader.load(MyConfig.class);
        assertNotNull(myConfig);

        String propertyA = myConfig.getPropertyA();
        assertEquals(VALUE_A, propertyA);

        String propertyB = myConfig.getPropertyB();
        assertEquals(VALUE_B, propertyB);
    }

    @Test
    @Ignore //does not work properly yet
    public void testTypeToken() {
        Loader loader = setup();
        TypeToken<MyGenericConfig<MyConvertibleType>> typeToken = new TypeToken<MyGenericConfig<MyConvertibleType>>() {};

        MyGenericConfig<MyConvertibleType> myConfig = loader.load(typeToken);
        assertNotNull(myConfig);

        MyConvertibleType propertyA = myConfig.getPropertyA();
        assertEquals(VALUE_A, propertyA.toString());

        MyConvertibleType propertyB = myConfig.getPropertyB();
        assertEquals(VALUE_B, propertyB.toString());
    }

    @Test(expected = NoSuchObjectException.class)
    public void testUnannotatedConfigObject() {
        Loader loader = setup();
        MyUnannotatedConfig myConfig = loader.load(MyUnannotatedConfig.class);
    }

    private Loader setup() {
        System.setProperty(PROPERTY_A, VALUE_A);
        System.setProperty(PROPERTY_B, VALUE_B);
        System.setProperty(PROPERTY_C, VALUE_C1 + "," + VALUE_C2 + "," + VALUE_C3);
        Loader loader = Loader.bootstrap();
        return loader;
    }
}
