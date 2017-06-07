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
package org.jnosql.artemis.column;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jnosql.diana.api.column.ColumnEntity;

import java.util.Objects;

/**
 * The default implementation to represents either {@link ColumnEntityPostPersist}
 */
class DefaultColumnEntityPostPersist implements ColumnEntityPostPersist {

    private final ColumnEntity entity;

    DefaultColumnEntityPostPersist(ColumnEntity entity) {
        this.entity = entity;
    }

    @Override
    public ColumnEntity getEntity() {
        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultColumnEntityPostPersist)) {
            return false;
        }
        DefaultColumnEntityPostPersist that = (DefaultColumnEntityPostPersist) o;
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
