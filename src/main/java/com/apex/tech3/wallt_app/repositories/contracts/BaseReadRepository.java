package com.apex.tech3.wallt_app.repositories.contracts;

import java.util.List;

public interface BaseReadRepository<T> {

    T get(int id);

    List<T> getAll();

    <V> T getByField(String fieldName, V value);
}
