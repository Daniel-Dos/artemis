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
package org.jnosql.artemis.column.query;

import org.jnosql.artemis.Converters;
import org.jnosql.artemis.DatabaseQualifier;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.Repository;
import org.jnosql.artemis.column.ColumnTemplate;
import org.jnosql.artemis.reflection.ClassRepresentations;
import org.jnosql.artemis.reflection.Reflections;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.PassivationCapable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Artemis discoveryBean to CDI extension to register {@link Repository}
 */
public class RepositoryColumnBean implements Bean<Repository>, PassivationCapable {

    private final Class type;

    private final BeanManager beanManager;

    private final Set<Type> types;

    private final String provider;

    private final Set<Annotation> qualifiers;

    /**
     * Constructor
     *
     * @param type        the tye
     * @param beanManager the beanManager
     * @param provider    the provider name, that must be a
     */
    public RepositoryColumnBean(Class type, BeanManager beanManager, String provider) {
        this.type = type;
        this.beanManager = beanManager;
        this.types = Collections.singleton(type);
        this.provider = provider;
        if (provider.isEmpty()) {
            this.qualifiers = new HashSet<>();
            qualifiers.add(DatabaseQualifier.ofColumn());
        } else {
            this.qualifiers = Collections.singleton(DatabaseQualifier.ofColumn(provider));
        }
    }

    @Override
    public Class<?> getBeanClass() {
        return type;
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return Collections.emptySet();
    }

    @Override
    public boolean isNullable() {
        return false;
    }

    @Override
    public Repository create(CreationalContext<Repository> creationalContext) {
        ClassRepresentations classRepresentations = getInstance(ClassRepresentations.class);
        ColumnTemplate repository = provider.isEmpty() ? getInstance(ColumnTemplate.class) :
                getInstance(ColumnTemplate.class, provider);
        Reflections reflections = getInstance(Reflections.class);
        Converters converters = getInstance(Converters.class);

        ColumnRepositoryProxy handler = new ColumnRepositoryProxy(repository,
                classRepresentations, type, reflections, converters);
        return (Repository) Proxy.newProxyInstance(type.getClassLoader(),
                new Class[]{type},
                handler);
    }


    private <T> T getInstance(Class<T> clazz) {
        Bean<T> bean = (Bean<T>) beanManager.getBeans(clazz).iterator().next();
        CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, clazz, ctx);
    }

    private <T> T getInstance(Class<T> clazz, String name) {
        Bean bean = beanManager.getBeans(clazz, DatabaseQualifier.ofColumn(name)).iterator().next();
        CreationalContext ctx = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, clazz, ctx);
    }


    @Override
    public void destroy(Repository instance, CreationalContext<Repository> creationalContext) {

    }

    @Override
    public Set<Type> getTypes() {
        return types;
    }

    @Override
    public Set<Annotation> getQualifiers() {
        return qualifiers;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return ApplicationScoped.class;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return Collections.emptySet();
    }

    @Override
    public boolean isAlternative() {
        return false;
    }

    @Override
    public String getId() {
        return type.getName() + '@' + DatabaseType.COLUMN + "-" + provider;
    }

}