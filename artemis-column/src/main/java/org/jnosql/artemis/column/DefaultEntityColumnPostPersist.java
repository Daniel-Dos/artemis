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


import java.util.Objects;

class DefaultEntityColumnPostPersist implements EntityColumnPostPersist {

    private final Object value;

    DefaultEntityColumnPostPersist(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultEntityColumnPostPersist)) {
            return false;
        }
        DefaultEntityColumnPostPersist that = (DefaultEntityColumnPostPersist) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultEntityColumnPostPersist{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
