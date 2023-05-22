/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.impl.exceptions;

/**
 *
 */
public class ConfigException extends RuntimeException {

    private static final long serialVersionUID = -3878061133593831054L;

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(Throwable throwable) {
        super(throwable);
    }

    public ConfigException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
