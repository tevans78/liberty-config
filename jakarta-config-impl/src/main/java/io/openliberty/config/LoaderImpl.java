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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import jakarta.config.ConfigException;
import jakarta.config.Configuration;
import jakarta.config.Loader;
import jakarta.config.NoSuchObjectException;
import jakarta.config.TypeToken;

public class LoaderImpl implements Loader {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(Class<T> type) {
        if (type == null) {
            throw new NullPointerException("Configuration type was null");
        }
        T instance = null;
        if (Loader.class == type || LoaderImpl.class == type) {
            instance = (T) this;
        } else {
            Configuration configuration = type.getAnnotation(Configuration.class);
            if (configuration != null) {
                instance = newInstance(type);
                instance = injectConfig(instance);
            } else {
                throw new NoSuchObjectException("Configuration type not found: " + type);
            }
        }
        return instance;
    }

    @Override
    public <T> T load(TypeToken<T> typeToken) {
        if (typeToken == null) {
            throw new NullPointerException("Configuration type was null");
        }

        T instance = newInstance(typeToken);
        injectConfig(instance);

        return instance;
    }

    private <T> T newInstance(Class<T> type) {
        T instance = null;
        try {
            Constructor<T> xtor = type.getConstructor();
            instance = xtor.newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ConfigException(e);
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    private <T> T newInstance(TypeToken<T> typeToken) {
        T instance = null;
        try {
            Type type = typeToken.type();
            Class<?> rawType = type instanceof Class<?> ? (Class<?>) type : (Class<?>) ((ParameterizedType) type).getRawType();
            Constructor<?> constructor = rawType.getConstructor();

            instance = (T) constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ConfigException(e);
        }
        return instance;
    }

    private <T> T injectConfig(T instance) {
        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            Type type = field.getGenericType();
            String name = field.getName();
            //Class<?> type = field.getType();
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
    private <T> T getProperty(String name, Type type) {
        String stringValue = getStringProperty(name);
        T value = null;
        if (String.class == type) {
            value = (T) stringValue;
        } else {
            value = deserialize(stringValue, Erasure.erase(type));
        }
        return value;
    }

    private String getStringProperty(String name) {
        return System.getProperty(name);
    }

    @SuppressWarnings("unchecked")
    private <T> T deserialize(String stringValue, Class<?> type) {
        T value = null;
        try {
            Method method = type.getMethod("valueOf", String.class);
            value = (T) method.invoke(null, stringValue);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ConfigException("static valueOf method not found for type " + type, e);
        }
        return value;
    }

}
