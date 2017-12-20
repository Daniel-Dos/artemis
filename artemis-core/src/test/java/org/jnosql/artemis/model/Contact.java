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
package org.jnosql.artemis.model;

import org.jnosql.artemis.Column;
import org.jnosql.artemis.Embeddable;

import java.util.Objects;

@Embeddable
public class Contact {

    @Column
    private ContactType type;

    @Column("contact_name")
    private String name;

    @Column
    private String information;

    Contact() {
    }

    private Contact(ContactType type, String name, String information) {
        this.type = type;
        this.name = name;
        this.information = information;
    }

    public ContactType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return type == contact.type &&
                Objects.equals(name, contact.name) &&
                Objects.equals(information, contact.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, information);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contact{");
        sb.append("type=").append(type);
        sb.append(", name='").append(name).append('\'');
        sb.append(", information='").append(information).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static ContactBuilder builder() {
        return new ContactBuilder();
    }

    public static class ContactBuilder {

        private ContactType type;

        private String name;

        private String information;

        private ContactBuilder() {
        }

        public ContactBuilder withType(ContactType type) {
            this.type = type;
            return this;
        }

        public ContactBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ContactBuilder withInformation(String information) {
            this.information = information;
            return this;
        }

        public Contact build() {
            return new Contact(type, name, information);
        }
    }
}
