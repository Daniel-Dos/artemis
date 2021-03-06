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
package org.jnosql.artemis.key;


import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;


/**
 * This interface that represents the common operation between an entity and KeyValueEntity
 */
public interface KeyValueTemplate {
    /**
     * Saves the entity
     *
     * @param entity the entity to be insert
     * @param <T>    the entity type
     * @throws NullPointerException when entity is null
     */
    <T> T put(T entity) throws NullPointerException;

    /**
     * Saves the entity with time to live
     *
     * @param entity the entity to be insert
     * @param ttl    the defined time to live
     * @param <T>    the entity type
     * @throws NullPointerException          when entity is null
     * @throws UnsupportedOperationException when expired time is not supported
     */
    <T> T put(T entity, Duration ttl) throws NullPointerException, UnsupportedOperationException;

    /**
     * Saves the {@link Iterable} of entities
     *
     * @param entities keys to be insert
     * @param <T>      the entity type
     * @throws NullPointerException when the iterable is null
     */
    default <T> Iterable<T> put(Iterable<T> entities) throws NullPointerException {
        Objects.requireNonNull(entities, "entities is required");
        return StreamSupport.stream(entities.spliterator(), false).map(this::put).collect(Collectors.toList());
    }

    /**
     * Saves the {@link Iterable} of entities with a defined time to live
     *
     * @param entities entities to be insert
     * @param ttl      the time to entity expire
     * @param <T>      the entity type
     * @throws NullPointerException          when the iterable is null
     * @throws UnsupportedOperationException when expired time is not supported
     */
    default<T> Iterable<T> put(Iterable<T> entities, Duration ttl) throws NullPointerException, UnsupportedOperationException {
        Objects.requireNonNull(entities, "entities is required");
        Objects.requireNonNull(ttl, "ttl is required");
        return StreamSupport.stream(entities.spliterator(), false).map(d -> put(d, ttl)).collect(toList());
    }

    /**
     * Finds the Value from a key
     *
     * @param key the key
     * @param <K> the key type
     * @param <T> the entity type
     * @return the {@link Optional} when is not found will return a {@link Optional#empty()}
     * @throws NullPointerException when the key is null
     */
    <K, T> Optional<T> get(K key, Class<T> clazz) throws NullPointerException;

    /**
     * Finds a list of values from keys
     *
     * @param keys the keys to be used in this query
     * @param <K>  the key type
     * @param <T>  the entity type
     * @return the list of result
     * @throws NullPointerException when either the keys or the entities values are null
     */
    <K, T> Iterable<T> get(Iterable<K> keys, Class<T> clazz) throws NullPointerException;

    /**
     * Removes an entity from key
     *
     * @param key the key bo be used
     * @param <K> the key type
     * @throws NullPointerException when the key is null
     */
    <K> void remove(K key) throws NullPointerException;

    /**
     * Removes entities from keys
     *
     * @param keys the keys to be used
     * @param <K>  the key type
     * @throws NullPointerException when the key is null
     */
    <K> void remove(Iterable<K> keys) throws NullPointerException;

}
