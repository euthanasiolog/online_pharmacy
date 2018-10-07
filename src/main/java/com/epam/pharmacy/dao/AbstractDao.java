package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.Entity;
import com.epam.pharmacy.exception.ApplicationException;

public interface AbstractDao<T extends Entity> {
    boolean create(T entity, String password) throws ApplicationException;
    T findById(int id);
    void update(T entity);
    void delete(T entity);

}
