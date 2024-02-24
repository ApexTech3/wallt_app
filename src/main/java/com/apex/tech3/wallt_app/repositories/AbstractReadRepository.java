package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.repositories.contracts.BaseReadRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class AbstractReadRepository<T> implements BaseReadRepository<T> {
    private final Class<T> clazz;
    protected final SessionFactory sessionFactory;


    public AbstractReadRepository(Class<T> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T get(int id) {
        return getByField("id", id);
    }

    @Override
    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + clazz.getName(), clazz).list();
        }
    }

    @Override
    public <V> T getByField(String fieldName, V value) {
        final String query = String.format("from %s where %s = :value", clazz.getName(), fieldName);
        final String notFoundMessage = String.format("%s with %s %s not found", clazz.getSimpleName(), fieldName, value);

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(query, clazz)
                    .setParameter("value", value)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new IllegalArgumentException(notFoundMessage));
        }
    }
}
