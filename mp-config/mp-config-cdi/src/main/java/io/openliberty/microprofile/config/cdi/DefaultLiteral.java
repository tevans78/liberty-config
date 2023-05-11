/*******************************************************************************
 * Copyright (c) 2017, 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package io.openliberty.microprofile.config.cdi;

import java.lang.annotation.Annotation;

import jakarta.enterprise.inject.Default;
import jakarta.enterprise.util.AnnotationLiteral;

/**
 * DefaultLiteral represents an instance of the Default annotation
 */
public class DefaultLiteral extends AnnotationLiteral<Default> implements Default {

    public static final Annotation INSTANCE = new DefaultLiteral();

}
