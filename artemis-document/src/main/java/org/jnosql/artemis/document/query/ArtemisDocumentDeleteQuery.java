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

import org.jnosql.diana.api.document.DocumentCondition;
import org.jnosql.diana.api.document.DocumentDeleteQuery;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class ArtemisDocumentDeleteQuery implements DocumentDeleteQuery {

    private final String documentCollection;

    private final DocumentCondition condition;

    ArtemisDocumentDeleteQuery(String documentCollection, DocumentCondition condition) {
        this.documentCollection = documentCollection;
        this.condition = condition;
    }

    @Override
    public String getDocumentCollection() {
        return documentCollection;
    }

    @Override
    public Optional<DocumentCondition> getCondition() {
        return Optional.ofNullable(condition);
    }

    @Override
    public List<String> getDocuments() {
        return Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentDeleteQuery)) {
            return false;
        }
        DocumentDeleteQuery that = (DocumentDeleteQuery) o;
        return Objects.equals(documentCollection, that.getDocumentCollection()) &&
                Objects.equals(condition, that.getCondition()) && Objects.equals(Collections.emptyList(), that.getDocuments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentCollection, condition, Collections.emptyList());
    }
}
