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

import org.eclipse.microprofile.config.spi.Converter;

/**
 *
 */
public abstract class PriorityConverter<T> implements Converter<T> {

    private static final long serialVersionUID = -3263691039509471899L;
    public static final int DEFAULT_CONVERTER_PRIORITY = 100;
    private final int priority;
    private final Class<T> type;

    public PriorityConverter(Class<T> type, int priority) {
        this.priority = priority;
        this.type = type;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return
     */
    public Class<T> getType() {
        return this.type;
    }
}
