package com.epam.pharmacy.dao.impl;

final class SQLScripts {
    static final String DOCTOR_SIGN_IN_SQL = "SELECT u.id, u.nickname, u.email, u.type FROM user u\n" +
            "WHERE u.nickname=? AND u.password = SHA2(?,512);";
    static final String CLIENT_SIGN_IN_SQL = "";
    static final String PHARMACIST_SIGN_IN_SQL = "";
    static final String CLIENT_CREATE = "";
    static final String DOCTOR_CREATE = "";
    static final String DRUG_CREATE = "";

    static final String CREATE_DRUG = "INSERT INTO item (name, inn, composition, form, dose, number, shelflife," +
            " price, recipe, availability, ordertime, archive) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    static final String FIND_DRUG_BY_ID = "SELECT name, inn, composition, form, dose, number, shelflife, price," +
            " recipe, availability, ordertime, archive" +
            "FROM drud WHERE id=?";

    private SQLScripts(){
        throw new IllegalStateException("Utility Class");
    }
}
