/*
 * Copyright 2017 Otavio Santana and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jnosql.artemis.column.query;


import org.jnosql.artemis.IdNotFoundException;
import org.jnosql.artemis.RepositoryAsync;
import org.jnosql.artemis.column.ColumnTemplateAsync;
import org.jnosql.artemis.reflection.ClassRepresentation;
import org.jnosql.artemis.reflection.ClassRepresentations;
import org.jnosql.artemis.reflection.FieldRepresentation;
import org.jnosql.artemis.reflection.Reflections;
import org.jnosql.diana.api.column.Column;
import org.jnosql.diana.api.column.ColumnCondition;
import org.jnosql.diana.api.column.ColumnDeleteQuery;
import org.jnosql.diana.api.column.ColumnQuery;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Proxy handle to generate {@link RepositoryAsync}
 *
 * @param <T> the type
 */
class ColumnRepositoryAsyncProxy<T> extends AbstractColumnRepositoryAsyncProxy<T> {

    private final Class<T> typeClass;

    private final ColumnTemplateAsync template;

    private final ColumnRepositoryAsync repository;

    private final ClassRepresentation classRepresentation;

    private final ColumnQueryParser queryParser;

    private final ColumnQueryDeleteParser deleteParser;


    ColumnRepositoryAsyncProxy(ColumnTemplateAsync template, ClassRepresentations classRepresentations, Class<?> repositoryType) {
        this.template = template;
        this.repository = new ColumnRepositoryAsync(template);
        this.typeClass = Class.class.cast(ParameterizedType.class.cast(repositoryType.getGenericInterfaces()[0])
                .getActualTypeArguments()[0]);
        this.classRepresentation = classRepresentations.get(typeClass);
        this.queryParser = new ColumnQueryParser();
        this.deleteParser = new ColumnQueryDeleteParser();
    }

    @Override
    protected RepositoryAsync getRepository() {
        return repository;
    }

    @Override
    protected ClassRepresentation getClassRepresentation() {
        return classRepresentation;
    }

    @Override
    protected ColumnQueryParser getQueryParser() {
        return queryParser;
    }

    @Override
    protected ColumnQueryDeleteParser getDeleteParser() {
        return deleteParser;
    }

    @Override
    protected ColumnTemplateAsync getTemplate() {
        return template;
    }


    class ColumnRepositoryAsync extends AbstractColumnRepositoryAsync implements RepositoryAsync {
        private static final Supplier<IdNotFoundException> KEY_NOT_FOUND_EXCEPTION_SUPPLIER = ()
                -> new IdNotFoundException("To use this resource you must annotaded a fiel with @org.jnosql.artemisId");

        private final ColumnTemplateAsync template;

        private final Reflections reflections;

        private final ClassRepresentation classRepresentation;

        ColumnRepositoryAsync(ColumnTemplateAsync repository, Reflections reflections, ClassRepresentation classRepresentation) {
            this.template = repository;
            this.reflections = reflections;
            this.classRepresentation = classRepresentation;
        }

        @Override
        protected ColumnTemplateAsync getTemplate() {
            return template;
        }

        @Override
        public void deleteById(Object id) throws NullPointerException {
            requireNonNull(id, "is is required");
            ColumnDeleteQuery query = ColumnDeleteQuery.of(getClassRepresentation().getName());
            String columnName = this.getIdField().getName();
            query.with(ColumnCondition.eq(Column.of(columnName, id)));
            getTemplate().delete(query);
        }

        @Override
        public void delete(Iterable entities) throws NullPointerException {
            requireNonNull(entities, "entities is required");
            entities.forEach(this::delete);
        }

        @Override
        public void delete(Object entity) throws NullPointerException {
            requireNonNull(entity, "entity is required");
            Object idValue = reflections.getValue(entity, this.getIdField().getField());
            requireNonNull(idValue, "id value is required");
            deleteById(idValue);
        }

        @Override
        public void existsById(Object id, Consumer callBack) throws NullPointerException {

        }

        @Override
        public void findById(Iterable ids, Consumer callBack) throws NullPointerException {

        }

        @Override
        public void findById(Object id, Consumer callBack) throws NullPointerException {
            requireNonNull(id, "id is required");

            ColumnQuery query = ColumnQuery.of(getClassRepresentation().getName());
            String columnName = this.getIdField().getName();
            query.with(ColumnCondition.eq(Column.of(columnName, id)));
            Consumer<List<?>> as;
            getTemplate().select(query, as);
        }

        private FieldRepresentation getIdField() {
            return getClassRepresentation().getId().orElseThrow(KEY_NOT_FOUND_EXCEPTION_SUPPLIER);
        }
    }
}
