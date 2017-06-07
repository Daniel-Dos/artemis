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


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jnosql.diana.api.document.DocumentEntity;

import java.util.Objects;

/**
 * The default implementation to represents {@link DocumentEntityPrePersist}
 */
class DefaultDocumentEntityPrePersist implements DocumentEntityPrePersist {

    private final DocumentEntity entity;

    DefaultDocumentEntityPrePersist(DocumentEntity entity) {
        this.entity = entity;
    }

    @Override
    public DocumentEntity getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultDocumentEntityPrePersist)) {
            return false;
        }
        DefaultDocumentEntityPrePersist that = (DefaultDocumentEntityPrePersist) o;
        return Objects.equals(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("entity", entity)
                .toString();
    }
}
