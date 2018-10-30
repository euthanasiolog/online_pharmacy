package com.epam.pharmacy.dao;


import java.util.List;

public interface AdminDao extends UserDao {
    boolean confirmUserRegistrationQuery (int userId) throws DaoException;

    boolean deleteUser (int userId) throws DaoException;

    boolean dropArchiveDrugTable () throws DaoException;

    boolean dropArchiveUserTable () throws DaoException;

    boolean dropArchiveRecipeTable () throws DaoException;

    boolean dropArchiveOrderTable () throws DaoException;
}
