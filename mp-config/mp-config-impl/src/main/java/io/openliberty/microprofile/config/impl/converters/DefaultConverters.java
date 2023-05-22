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

import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.eclipse.microprofile.config.spi.Converter;

import io.openliberty.microprofile.config.impl.exceptions.ConfigException;

/**
 * The helper class returns all the built-in converters.
 *
 */
public class DefaultConverters {

    private static final PriorityConverterMap defaultConverters = new PriorityConverterMap();

    static {
        defaultConverters.addConverter(new StringConverter());
        defaultConverters.addConverter(new BooleanConverter());
        defaultConverters.addConverter(new AutomaticConverter<>(Integer.class));
        defaultConverters.addConverter(new AutomaticConverter<>(Long.class));
        defaultConverters.addConverter(new AutomaticConverter<>(Short.class));
        defaultConverters.addConverter(new AutomaticConverter<>(Byte.class));
        defaultConverters.addConverter(new AutomaticConverter<>(Double.class));
        defaultConverters.addConverter(new AutomaticConverter<>(Float.class));

        defaultConverters.addConverter(new OptionalIntConverter());
        defaultConverters.addConverter(new OptionalLongConverter());
        defaultConverters.addConverter(new OptionalDoubleConverter());

        //add the class converter
        defaultConverters.addConverter(new ClassConverter());

        defaultConverters.addConverter(new CharacterConverter());

        defaultConverters.setUnmodifiable();
    }

    /**
     * @return defaultConverters
     */
    public static PriorityConverterMap getDefaultConverters() {
        return defaultConverters;
    }

    /**
     * @return discoveredInventors
     */
    public static PriorityConverterMap getDiscoveredConverters(ClassLoader classLoader) {
        PriorityConverterMap discoveredConverters = new PriorityConverterMap();

        //load config sources using the service loader
        try {
            @SuppressWarnings("rawtypes")
            ServiceLoader<Converter> sl = ServiceLoader.load(Converter.class, classLoader);
            for (Converter<?> converter : sl) {
                UserConverter<?> userConverter = UserConverter.newInstance(converter);
                discoveredConverters.addConverter(userConverter);
            }
        } catch (ServiceConfigurationError e) {
            throw new ConfigException("Unable to discovert converters", e);
        }

        return discoveredConverters;
    }
}
