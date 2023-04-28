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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jakarta.config.ConfigException;
import jakarta.config.Loader;
import jakarta.config.TypeToken;

public class LoaderImpl implements Loader {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(Class<T> type) {
        T instance = null;
        if (Loader.class == type || LoaderImpl.class == type) {
            instance = (T) this;
        } else {
            instance = newInstance(type);
            instance = injectConfig(instance);
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(TypeToken<T> type) {
        T instance = null;
        //TODO this is a massive over simplification
        Class<?> erased = type.erase();
        instance = (T) load(erased);
        return instance;
    }

    private <T> T newInstance(Class<T> type) {
        T instance = null;
        try {
            Constructor<T> xtor = type.getConstructor();
            instance = xtor.newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ConfigException(e);
        }
        return instance;
    }

    private <T> T injectConfig(T instance) {
        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();
            Class<?> type = field.getType();
            Object value = getProperty(name, type);
            setField(instance, field, value);
        }

        return instance;
    }

    private void setField(Object instance, Field field, Object value) {
        boolean accessible = true;
        if (!field.canAccess(instance)) {
            accessible = false;
            field.setAccessible(true);
        }
        try {
            field.set(instance, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new ConfigException(e);
        } finally {
            if (!accessible) {
                field.setAccessible(false);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getProperty(String name, Class<T> type) {
        String stringValue = getStringProperty(name);
        T value = null;
        if (String.class == type) {
            value = (T) stringValue;
        } else {
            value = deserialize(stringValue, type);
        }
        return value;
    }

    private String getStringProperty(String name) {
        return System.getProperty(name);
    }

    @SuppressWarnings("unchecked")
    private <T> T deserialize(String stringValue, Class<T> type) {
        T value = null;
        try {
            Method method = type.getMethod("valueOf", String.class);
            value = (T) method.invoke(null, stringValue);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ConfigException(e);
        }
        return value;
    }

}
