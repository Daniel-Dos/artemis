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
package org.jnosql.artemis.document;


import org.jnosql.diana.api.document.DocumentDeleteQuery;

import java.util.Objects;

class DefaultDocumentDeleteQueryExecute implements DocumentDeleteQueryExecute {

    private final DocumentDeleteQuery query;

    DefaultDocumentDeleteQueryExecute(DocumentDeleteQuery query) {
        this.query = Objects.requireNonNull(query, "query is required");
    }

    @Override
    public DocumentDeleteQuery getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultDocumentDeleteQueryExecute)) return false;
        DefaultDocumentDeleteQueryExecute that = (DefaultDocumentDeleteQueryExecute) o;
        return Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(query);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultDocumentDeleteQueryExecute{");
        sb.append("query=").append(query);
        sb.append('}');
        return sb.toString();
    }
}
