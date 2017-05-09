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
package org.jnosql.artemis.document;


import org.jnosql.diana.api.ExecuteAsyncQueryException;
import org.jnosql.diana.api.NonUniqueResultException;
import org.jnosql.diana.api.document.DocumentDeleteQuery;
import org.jnosql.diana.api.document.DocumentQuery;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

/**
 * This interface that represents the common operation between an entity and DocumentCollectionEntity.
 *
 * @see org.jnosql.diana.api.document.DocumentCollectionManagerAsync
 */
public interface DocumentTemplateAsync {

    /**
     * Saves an entity asynchronously
     *
     * @param entity entity to be saved
     * @param <T>    the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when entity are null
     */
    <T> void insert(T entity) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Saves an entity asynchronously with time to live
     *
     * @param entity entity to be saved
     * @param <T>    the instance type
     * @param ttl    the time to live
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when either entity or ttl are null
     */
    <T> void insert(T entity, Duration ttl) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Saves entities asynchronously, by default it's just run for each saving using
     * {@link DocumentTemplateAsync#insert(Object)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when entities is null
     */
    default <T> void insert(Iterable<T> entities) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException {
        requireNonNull(entities, "entities is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(this::insert);
    }

    /**
     * Saves entities asynchronously with time to live, by default it's just run for each saving using
     * {@link DocumentTemplateAsync#insert(Object)} (Object, Duration)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @param ttl      time to live
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when either entities or ttl are null
     */
    default <T> void insert(Iterable<T> entities, Duration ttl) throws NullPointerException {
        requireNonNull(entities, "entities is required");
        requireNonNull(ttl, "ttl is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(d -> insert(d, ttl));
    }

    /**
     * Saves an entity asynchronously
     *
     * @param entity   entity to be saved
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the saved entity within parameters
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when either entity or callBack are null
     */
    <T> void insert(T entity, Consumer<T> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Saves an entity asynchronously with time to live
     *
     * @param entity   entity to be saved
     * @param ttl      time to live
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the saved entity within parameters
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when either entity or ttl or callBack are null
     */
    <T> void insert(T entity, Duration ttl, Consumer<T> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Updates an entity asynchronously
     *
     * @param entity   entity to be updated
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the updated entity within parametersa
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when either entity or callback are null
     */
    <T> void update(T entity, Consumer<T> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;


    /**
     * Updates an entity asynchronously
     *
     * @param entity entity to be updated
     * @param <T>    the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when entity is null
     */
    <T> void update(T entity) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Updates entities asynchronously, by default it's just run for each saving using
     * {@link DocumentTemplate#update(Object)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when entities is null
     */
    default <T> void update(Iterable<T> entities) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException {
        requireNonNull(entities, "entities is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(this::update);
    }

    /**
     * Deletes an entity asynchronously
     *
     * @param query query to delete an entity
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when query is null
     */
    void delete(DocumentDeleteQuery query) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;


    /**
     * Deletes an entity asynchronously
     *
     * @param query    query to delete an entity
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the null within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to delete asynchronous
     * @throws NullPointerException          when either query or callback are null
     */
    void delete(DocumentDeleteQuery query, Consumer<Void> callBack) throws ExecuteAsyncQueryException,
            UnsupportedOperationException, NullPointerException;

    /**
     * Finds entities from query asynchronously
     *
     * @param query    query to select entities
     * @param <T>      the instance type
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the result of query within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when either query or callback are null
     */
    <T> void select(DocumentQuery query, Consumer<List<T>> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;


    /**
     * Execute a query to consume an unique result
     *
     * @param query    the query
     * @param callBack the callback
     * @param <T>      the type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to insert asynchronous
     * @throws NullPointerException          when either query or callback are null
     * @throws NonUniqueResultException      when it returns more than one result
     */
    default <T> void singleResult(DocumentQuery query, Consumer<Optional<T>> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException, NonUniqueResultException {

        requireNonNull(callBack, "callBack is required");

        Consumer<List<T>> singleCallBack = entities -> {
            if (entities.isEmpty()) {
                callBack.accept(Optional.empty());
            } else if (entities.size() == 1) {
                callBack.accept(Optional.of(entities.get(0)));
            } else {
                throw new NonUniqueResultException("The query returns more than one entity, query: " + query);
            }
        };
        select(query, singleCallBack);

    }
}
