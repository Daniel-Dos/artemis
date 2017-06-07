/*
 *  Copyright (c) 2017 Otávio Santana and others
 *   All rights reserved. This program and the accompanying materials
 *   are made available under the terms of the Eclipse Public License v1.0
 *   and Apache License v2.0 which accompanies this distribution.
 *   The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 *   and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 *   You may elect to redistribute this code under either of these licenses.
 *
 *   Contributors:
 *
 *   Otavio Santana
 */

/**
 * The artemis-validation is a subproject of Artemis that has the goals to validate the data using bean validation.
 * It uses the Bean validation API to do this validation before the event is persist:
 * So, it listens:
 * <p>EntityPrePersist</p>
 */
package org.jnosql.artemis.validation;