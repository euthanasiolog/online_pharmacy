package com.epam.online_pharmacy.dao;

import com.epam.online_pharmacy.connection.ConnectionPool;
import com.epam.online_pharmacy.entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface AbstractDao {
    void create(Entity entity);
    Entity findById(int id);
    void update(Entity entity);
    void delete(Entity entity);

}
