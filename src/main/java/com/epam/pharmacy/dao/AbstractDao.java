package com.epam.pharmacy.dao;

import com.epam.pharmacy.entity.Entity;

public interface AbstractDao {
    void create(Entity entity);
    Entity findById(int id);
    void update(Entity entity);
    void delete(Entity entity);

}
