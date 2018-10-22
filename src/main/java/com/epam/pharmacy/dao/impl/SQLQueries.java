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
            "VALUE (?, SHA2(?, 512), ?, ?, ?, ?, ?, ?); SELECT LAST_INSERT_ID()";
    static final String CREATE_CLIENT = "INSERT INTO CLIENT (id, discount)\n" +
            "VALUES (LAST_INSERT_ID(), ?)";
    static final String CREATE_DOCTOR = "INSERT INTO doctor (`id`, `specialization`, `workplace`)\n" +
            "VALUES (LAST_INSERT_ID(), ?, ?)";
    static final String CREATE_DRUG = "INSERT INTO drug (name, inn, composition, form, dose, number, shelflife," +
            " price, recipe, availability, ordertime, archive) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    static final String FIND_DRUG_BY_ID = "SELECT id, name, inn, composition, form, dose, number, shelflife, price," +
            " recipe, availability, ordertime, archive" +
            "FROM drud WHERE id=?";
    static final String SHOW_ALL_DRUGS = "SELECT id, name, inn, composition, form, dose, number, shelflife, price," +
            " recipe, availability, ordertime " +
            " FROM drug WHERE archive='0'";
    static final String DELETE_USER = "UPDATE user SET `archive`='1' WHERE id=?";
    static final String SEARCH_DRUG = "SELECT d.id, d.name, d.inn, d.form, d.composition, d.dose, d.number FROM drug d\n" +
            "d.shelflife, d.price,\" +\n" +
            "            \" d.recipe, d.availability, d.ordertime  WHERE d.name LIKE '%?%' OR d.inn LIKE ?";
    static final String PUT_DRUG_IN_CART = "insert into cart (id_client, id_drug, number, payment) \n" +
            "values (?, ?, ?, ?)";
    static final String UPDATE_DRUG_IN_CART = "UPDATE cart SET number=?, payment=? WHERE id=?";
    static final String DO_PAYMENT = "update cart set payment='1' where id=?";
    static final String DELETE_DRUG_FROM_CART = "update cart set archive='1' where \n" +
            "id_client=? and id_drug=?";
    static final String CREATE_RECIPE = "insert into recipe  (recipe.patient_id, recipe.drug_id, recipe.doctor_id, \n" +
            "recipe.serialnumber, recipe.type, recipe.from, recipe.to) values (?,?,?,?,?,?,?)";
    static final String GET_CLIENT_RECIPIES = "SELECT r.`id`, r.serialnumber, r.`doctor_id`, r.`drug_id`, r.`patient_id`, r.`type`, r.`from`, r.`to` \n" +
            "FROM recipe r \n" +
            "WHERE r.`patient_id`=?";
    static final String USE_RECIPE = "UPDATE recipe r ON r.`used`='1'\n" +
            "WHERE r.`patient_id`=?";
    static final String DELETE_RECIPE = "UPDATE recipe r ON r.`archive`='1'\n" +
            "WHERE r.`patient_id`=?";
    static final String GET_CLIENT_CART = "SELECT c.`id`, c.`id_drug`, c.`number`, c.`payment`\t\n" +
            "FROM cart c WHERE c.`id_client`=?";


    private SQLQueries(){
        throw new IllegalStateException("Utility Class");
    }
}
