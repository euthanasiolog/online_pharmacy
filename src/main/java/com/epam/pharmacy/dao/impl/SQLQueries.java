package com.epam.pharmacy.dao.impl;

final class SQLQueries {
    static final String USER_SIGN_IN = "select u.id, u.nickname, u.email, u.firstname, u.lastname, u.patronymic, u.birthdate, u.type\n" +
            "from user u where u.nickname=? and u.`password`=sha2(?,512) and u.`archive`='0'";
    static final String FIND_USER_BY_ID = "select u.id, u.nickname, u.email, u.firstname, u.lastname, u.patronymic, u.birthdate, u.type\n" +
            "from user u where u.id=? and u.`archive`='0'";
    static final String DOCTOR_SIGN_IN_SQL = "Select d.`specialization`, d.`workplace` from doctor d\n" +
            "where d.`id`=?";
    static final String CLIENT_SIGN_IN_SQL = "SELECT c.`discount` FROM CLIENT c WHERE c.`id`=?";
    static final String CREATE_USER = "INSERT INTO USER \n" +
            "(nickname, PASSWORD, email, firstname, lastname, patronymic, TYPE, birthdate) \n" +
            "VALUE (?, SHA2(?, 512), ?, ?, ?, ?, ?, ?)";
    static final String CREATE_CLIENT = "INSERT INTO CLIENT (id, discount)\n" +
            "VALUES (LAST_INSERT_ID, ?)";
    static final String CREATE_DOCTOR = "INSERT INTO doctor (`id`, `specialization`, `workplace`)\n" +
            "VALUES (LAST_INSERT_ID, ?, ?)";
    static final String CREATE_DRUG = "INSERT INTO item (name, inn, composition, form, dose, number, shelflife," +
            " price, recipe, availability, ordertime, archive) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    static final String FIND_DRUG_BY_ID = "SELECT name, inn, composition, form, dose, number, shelflife, price," +
            " recipe, availability, ordertime, archive" +
            "FROM drud WHERE id=?";
    static final String SHOW_ALL_DRUGS = "SELECT name, inn, composition, form, dose, number, shelflife, price," +
            " recipe, availability, ordertime " +
            " FROM drug WHERE archive='0'";
    static final String DELETE_USER = "UPDATE user SET `archive`='1' WHERE id=?";

    private SQLQueries(){
        throw new IllegalStateException("Utility Class");
    }
}
