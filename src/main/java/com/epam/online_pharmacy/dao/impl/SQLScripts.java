package com.epam.online_pharmacy.dao.impl;

public class SQLScripts {
    final static String SIGN_IN_SQL = "SELECT u.nickname, u.emaill, u.type FROM user u\n" +
            "WHERE u.nickname=? AND u.password = SHA2(?,512);";
    final static String CREATE_DRUG = "INSERT INTO drug (name, inn, composition, form, dose, number, shelflife," +
            " price, recipe, availability, ordertime, archive) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    final static String FIND_DRUG_BY_ID = "SELECT name, inn, composition, form, dose, number, shelflife, price," +
            " recipe, availability, ordertime, archive" +
            "FROM drud WHERE id=?";
}
