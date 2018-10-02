package com.epam.online_pharmacy.dao;

import com.epam.online_pharmacy.entity.Entity;

public interface AbstractDao {
    void create(Entity entity);
    Entity findById(int id);
    void update(Entity entity);
    void delete(Entity entity);

}
