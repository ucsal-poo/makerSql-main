package com.sql.core.pa;

import java.util.List;

public interface RepositoryBase<E, I> {
    void save(E entity);
    void delete(E entity);
    List<E> findAll();
    E findById(I id);
}
