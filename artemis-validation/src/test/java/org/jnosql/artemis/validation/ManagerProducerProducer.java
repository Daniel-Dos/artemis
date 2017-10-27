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
package org.jnosql.artemis.validation;


import org.jnosql.diana.api.column.Column;
import org.jnosql.diana.api.column.ColumnEntity;
import org.jnosql.diana.api.column.ColumnFamilyManager;
import org.jnosql.diana.api.document.Document;
import org.jnosql.diana.api.document.DocumentCollectionManager;
import org.jnosql.diana.api.document.DocumentEntity;
import org.jnosql.diana.api.key.BucketManager;
import org.mockito.Mockito;

import javax.enterprise.inject.Produces;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;

public class ManagerProducerProducer {


    @Produces
    public BucketManager getBucketManager() {
        BucketManager manager = Mockito.mock(BucketManager.class);
        return manager;
    }

    @Produces
    public DocumentCollectionManager getDocumentCollectionManager() {
        DocumentCollectionManager collectionManager = Mockito.mock(DocumentCollectionManager.class);


        DocumentEntity entity = DocumentEntity.of("person");
        entity.add(Document.of("name", "Ada"));
        entity.add(Document.of("age", 10));
        entity.add(Document.of("salary", BigDecimal.TEN));
        entity.add(Document.of("phones", Arrays.asList("22342342")));

        when(collectionManager.insert(Mockito.any(DocumentEntity.class))).thenReturn(entity);
        when(collectionManager.update(Mockito.any(DocumentEntity.class))).thenReturn(entity);
        return collectionManager;
    }

    @Produces
    public ColumnFamilyManager getColumnFamilyManager() {
        ColumnFamilyManager columnFamilyManager = Mockito.mock(ColumnFamilyManager.class);

        ColumnEntity entity = ColumnEntity.of("person");
        entity.add(Column.of("name", "Ada"));
        entity.add(Column.of("age", 10));
        entity.add(Column.of("salary", BigDecimal.TEN));
        entity.add(Column.of("phones", Arrays.asList("22342342")));

        when(columnFamilyManager.insert(Mockito.any(ColumnEntity.class))).thenReturn(entity);
        when(columnFamilyManager.update(Mockito.any(ColumnEntity.class))).thenReturn(entity);


        return columnFamilyManager;
    }
}
