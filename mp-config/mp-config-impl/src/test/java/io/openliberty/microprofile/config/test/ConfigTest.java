/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.junit.BeforeClass;
import org.junit.Test;

import io.openliberty.microprofile.config.impl.sources.SystemPropertyConfigSource;

/**
 * Unit test for MicroProfile Config.
 */
public class ConfigTest {

    @BeforeClass
    public static void before() {
        System.setProperty("key1", "value1");
//        System.getenv().put("key2", "value2");
//        System.getenv().put("key.3", "value3");
//        System.getenv().put("key_4", "value4");
//        System.getenv().put("KEY_5", "value5");
    }

    @Test
    public void testConfig() {
        ConfigProviderResolver provider = ConfigProviderResolver.instance();
        Config config = provider.getConfig();

        String value = config.getValue("key1", String.class);
        assertEquals("value1", value);
    }

    @Test
    public void testConfigValue() {
        ConfigProviderResolver provider = ConfigProviderResolver.instance();
        Config config = provider.getConfig();

        ConfigValue configValue = config.getConfigValue("key1");
        assertEquals(SystemPropertyConfigSource.SYSTEM_PROPERTIES_DEFAULT_ORDINAL, configValue.getSourceOrdinal());
    }

//    @Test
//    public void testEnvName() {
//        ConfigProviderResolver provider = ConfigProviderResolver.instance();
//        Config config = provider.getConfig();
//
//        String value2 = config.getValue("key2", String.class);
//        assertEquals("value2", value2);
//
//        String value3 = config.getValue("key.3", String.class);
//        assertEquals("value3", value3);
//
//        String value4 = config.getValue("key%4", String.class);
//        assertEquals("value4", value4);
//
//        String value5 = config.getValue("key 5", String.class);
//        assertEquals("value5", value5);
//
//    }

}
