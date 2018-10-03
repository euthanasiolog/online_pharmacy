package com.epam.pharmacy.dao;

import com.epam.pharmacy.entity.Entity;

import java.sql.SQLException;

public interface AbstractDao {
    boolean create(Entity entity, String password) throws SQLException;
    Entity findById(int id);
    void update(Entity entity);
    void delete(Entity entity);
}
