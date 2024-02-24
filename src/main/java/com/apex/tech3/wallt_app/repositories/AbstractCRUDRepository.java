package com.apex.tech3.wallt_app.repositories;

import com.apex.tech3.wallt_app.repositories.contracts.BaseCRUDRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class AbstractCRUDRepository<T> extends AbstractReadRepository<T> implements BaseCRUDRepository<T> {


    public AbstractCRUDRepository(Class<T> clazz, SessionFactory sessionFactory) {
        super(clazz, sessionFactory);
    }

    @Override
    public T create(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public T update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    @Override
    public void delete(int id) {

    }
}
