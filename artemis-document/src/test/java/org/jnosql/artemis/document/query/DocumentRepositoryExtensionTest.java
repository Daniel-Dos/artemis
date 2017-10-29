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
package org.jnosql.artemis.document.query;

import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.PersonRepository;
import org.jnosql.artemis.PersonRepositoryAsync;
import org.jnosql.artemis.CDIJUnit4Runner;
import org.jnosql.artemis.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(CDIJUnit4Runner.class)
public class DocumentRepositoryExtensionTest {


    @Inject
    @Database(value = DatabaseType.DOCUMENT)
    private PersonRepository repository;

    @Inject
    @Database(value = DatabaseType.DOCUMENT, provider = "documentRepositoryMock")
    private PersonRepository repositoryMock;

    @Inject
    @Database(value = DatabaseType.DOCUMENT)
    private PersonRepositoryAsync repositoryAsync;

    @Inject
    @Database(value = DatabaseType.DOCUMENT, provider = "documentRepositoryMock")
    private PersonRepositoryAsync repositoryMockAsync;

    @Test
    public void shouldIniciateAsync() {
        assertNotNull(repositoryAsync);
        repositoryAsync.save(Person.builder().build());
    }

    @Test
    public void shouldGetQualifierAsync() {
        assertNotNull(repositoryMockAsync);
        repositoryMockAsync.save(Person.builder().build());
    }


    @Test
    public void shouldInitiate() {
        assertNotNull(repository);
        Person person = repository.save(Person.builder().build());
        assertEquals("Default", person.getName());
    }

    @Test
    public void shouldUseMock(){
        assertNotNull(repositoryMock);
        Person person = repositoryMock.save(Person.builder().build());
        assertEquals("documentRepositoryMock", person.getName());
    }
}