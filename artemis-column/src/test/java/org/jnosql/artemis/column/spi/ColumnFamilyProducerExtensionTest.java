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
package org.jnosql.artemis.column.spi;

import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.WeldJUnit4Runner;
import org.jnosql.artemis.column.ColumnTemplate;
import org.jnosql.artemis.column.ColumnTemplateAsync;
import org.jnosql.artemis.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(WeldJUnit4Runner.class)
public class ColumnFamilyProducerExtensionTest {

    @Inject
    @Database(value = DatabaseType.COLUMN, provider = "columnRepositoryMock")
    private ColumnTemplate managerMock;

    @Inject
    private ColumnTemplate manager;

    @Inject
    @Database(value = DatabaseType.COLUMN, provider = "columnRepositoryMock")
    private ColumnTemplateAsync managerMockAsync;

    @Inject
    private ColumnTemplateAsync managerAsync;

    @Test
    public void shouldInstance() {
        Assert.assertNotNull(manager);
        Assert.assertNotNull(managerMock);
    }

    @Test
    public void shouldSave() {
        Person person = manager.insert(Person.builder().build());
        Person personMock = managerMock.insert(Person.builder().build());

        assertEquals("Default", person.getName());
        assertEquals("columnRepositoryMock", personMock.getName());
    }

    @Test
    public void shouldSaveAsync() {
        managerAsync.insert(Person.builder().build());
        managerMockAsync.insert(Person.builder().build());
    }
}