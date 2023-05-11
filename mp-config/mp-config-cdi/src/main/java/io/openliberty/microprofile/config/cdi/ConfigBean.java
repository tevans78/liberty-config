/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.cdi;

import java.lang.annotation.Annotation;

import org.eclipse.microprofile.config.Config;

import io.openliberty.microprofile.config.impl.ConfigImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.spi.CreationalContext;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;

/**
 *
 */
public class ConfigBean extends AbstractConfigBean<Config> implements Bean<Config> {

    /**
     * @param beanManager
     * @param type
     * @param qualifier
     */
    public ConfigBean(BeanManager beanManager) {
        super(beanManager, Config.class, DefaultLiteral.INSTANCE);
    }

    /** {@inheritDoc} */
    @Override
    public Config create(CreationalContext<Config> creationalContext) {
        return new ConfigImpl();
    }

    /** {@inheritDoc} */
    @Override
    public void destroy(Config instance, CreationalContext<Config> creationalContext) {
        creationalContext.release();
    }

    /** {@inheritDoc} */
    @Override
    public Class<? extends Annotation> getScope() {
        return ApplicationScoped.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class<?> getBeanClass() {
        return Config.class;
    }

}
