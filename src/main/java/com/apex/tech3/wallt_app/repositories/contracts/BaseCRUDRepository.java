package com.apex.tech3.wallt_app.repositories.contracts;

public interface BaseCRUDRepository<T> extends BaseReadRepository<T> {
    T create(T entity);

    T update(T entity);

    void delete(int id);
}
