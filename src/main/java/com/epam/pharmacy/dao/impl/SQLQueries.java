package com.epam.pharmacy.dao.impl;

final class SQLQueries {
    static final String USER_SIGN_IN = "select u.id, u.nickname, u.email, u.firstname, u.lastname, u.patronymic, u.birthdate, u.type\n" +
            "from user u where u.nickname=? and u.`password`=sha2(?,512) and u.`archive`='0'";
    static final String FIND_USER_BY_ID = "select u.id, u.nickname, u.email, u.firstname, u.lastname, u.patronymic, u.birthdate, u.type\n" +
            "from user u where u.id=? and u.`archive`='0'";
    static final String DOCTOR_SIGN_IN_SQL = "Select d.`specialization`, d.`workplace` from doctor d\n" +
            "where d.`id`=?";
    static final String CLIENT_SIGN_IN_SQL = "SELECT c.`discount` FROM CLIENT c WHERE c.`id`=?";
    static final String CREATE_USER = "INSERT INTO USER " +
            "(nickname, PASSWORD, email, firstname, lastname, patronymic, TYPE, birthdate) " +
            "VALUE (?, sha2(?, 512), ?, ?, ?, ?, ?, ?)";
    static final String CREATE_USER_QUERY = "INSERT INTO USER r (r.nickname, r.PASSWORD, r.email, r.firstname, r.lastname, r.patronymic, r.TYPE, r.birthdate, r.archive)\n" +
            "VALUE (?, SHA2(?, 512), ?, ?, ?, ?, ?, ?, 2)";
    static final String GET_LAST_ID = "SELECT LAST_INSERT_ID()";
    static final String CREATE_CLIENT = "INSERT INTO CLIENT (id, discount)\n" +
            "VALUES (LAST_INSERT_ID(), ?)";
    static final String CREATE_DOCTOR = "INSERT INTO doctor (`id`, `specialization`, `workplace`)\n" +
            "VALUES (LAST_INSERT_ID(), ?, ?)";
    static final String CREATE_DRUG = "INSERT INTO drug (name, inn, composition, form, dose, number, shelflife," +
            " price, recipe, availability, amount, ordertime, archive, annotation) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    static  final String UPDATE_DRUG = "update drug d set d.name=?, d.inn=?, d.composition=?, d.form=?, d.dose=?, d.number=?, d.shelflife=?, d.price=?, \n" +
            "d.recipe=?, d.availability=?, d.amount=?, d.ordertime=?, d.archive=?, d.annotation=? where d.id=?";
    static final String FIND_DRUG_BY_ID = "SELECT id, name, inn, composition, form, dose, number, shelflife, price," +
            " recipe, availability, ordertime, amount, archive, annotation " +
            " FROM drug WHERE id=?";
    static final String SHOW_ALL_DRUGS = "SELECT id, name, inn, composition, form, dose, number, shelflife, price," +
            " recipe, availability, amount, ordertime, annotation " +
            " FROM drug WHERE archive='0'";
    static final String DELETE_USER = "UPDATE user SET `archive`='1' WHERE id=?";
    static final String SEARCH_DRUG_1 = "SELECT d.id, d.name, d.inn, d.form, d.composition, d.dose, d.number, d.shelflife, d.price,\n" +
            "d.recipe, d.availability, d.amount, d.ordertime, d.annotation FROM drug d   WHERE d.name LIKE '%";
    static final String SEARCH_DRUG_2 = "%' OR d.inn LIKE '%";
    static final String SEARCH_DRUG_3 = "%'";
    static final String PUT_DRUG_IN_CART = "insert into cart (id_client, id_drug, number, payment) \n" +
            "values (?, ?, ?, ?)";
    static final String UPDATE_DRUG_IN_CART = "UPDATE cart SET number=?, payment=?, archive=? WHERE id=?";
    static final String DO_PAYMENT = "update cart set payment='1' where id=?";
    static final String DELETE_DRUG_FROM_CART = "update cart set archive='1' where \n" +
            "id_client=? and id_drug=?";
    static final String CREATE_RECIPE = "insert into recipe  (recipe.patient_id, recipe.drug_id, recipe.doctor_id, \n" +
            "recipe.serialnumber, recipe.type, recipe.from, recipe.to) values (?,?,?,?,?,?,?)";
    static final String GET_CLIENT_RECIPES = "SELECT r.`id`, r.`patient_id`, r.`drug_id`, r.`doctor_id`, r.serialnumber, r.`type`, r.`from`, r.`to`, r.`used` \n" +
            "FROM recipe r \n" +
            "WHERE r.`patient_id`=? AND r.`archive`=0";
    static final String USE_RECIPE = "UPDATE recipe r ON r.`used`='1' \n" +
            "WHERE r.`id`=?";
    static final String DELETE_RECIPE = "UPDATE recipe r ON r.`archive`='1' \n" +
            "WHERE r.`id`=?";
    static final String GET_CLIENT_CART = "SELECT c.`id`, c.`id_drug`, c.`number`, c.`payment` \n" +
            "FROM cart c WHERE c.`id_client`=?";
    static final String GET_ORDER_BY_ID = "SELECT c.`id_client`cart, c.`id_drug`, c.`number`, c.`payment` FROM cart c WHERE c.id=?";
    static final String CHECK_USER_ROLE ="SELECT u.`type` FROM USER u WHERE u.`nickname`=? AND u.`password`=sha2(?,512) and u.`archive`='0'";
    static final String FIND_DOCTOR_EMPTY_RECIPES = "SELECT r.`id`, r.`patient_id`, r.`drug_id`, r.`doctor_id`, r.serialnumber, r.`type`, r.`from`, r.`to`, r.`used` \n" +
            "FROM recipe r WHERE r.`doctor_id`=? AND r.`from`=NULL AND r.`to`=NULL";
    static final String FIND_ALL_DOCTOR_RECIPES = "SELECT r.`id`, r.`patient_id`, r.`doctor_id`, r.`drug_id`, r.`type`, r.`serialnumber`, \n" +
            "r.`from`, r.`to`, r.`used`\n" +
            "FROM recipe r WHERE r.`doctor_id`=? AND r.`archive`=0";
    static final String SELECT_ALL_CLIENT_RECIPES = "SELECT r.`id`, r.`patient_id`,r.`drug_id`, r.`doctor_id`, r.`serialnumber`,  r.`type`, \n" +
            "r.`from`, r.`to`, r.`used`\n" +
            "FROM recipe r WHERE r.`patient_id`=?";
    static final String UPDATE_RECIPE = "UPDATE recipe r SET r.`patient_id`=?,  r.`drug_id`=?, r.`doctor_id`=?, r.`serialnumber`=?, r.`type`=?,  \n" +
            "r.`from`=?, r.`to`=?, r.`used`=? WHERE r.`id`=?";
    static final String FIND_RECIPE_BY_ID = "SELECT r.`id`, r.`patient_id`, r.`drug_id`, r.`doctor_id`, r.serialnumber, r.`type`, " +
            "r.`from`, r.`to`, r.`used` FROM recipe r WHERE r.`id`=?";
    static final String DELETE_DRUG = "UPDATE drug d SET d.`archive`=1 WHERE d.`id`=?";
    static final String RESET_DATE_IN_RECIPE = "UPDATE recipe r SET r.`from`=NULL, r.`to`=NULL WHERE r.`id`=?";
    static final String GET_DOCTORS = "SELECT u.`id`, u.email, u.nickname, u.`firstname`, u.`lastname`, u.`patronymic`, u.`birthdate`, d.specialization, d.workplace FROM USER u \n" +
            "JOIN doctor d ON u.id = d.id WHERE u.`archive`=0";
    static final String FIND_DOCTOR_BY_ID = "SELECT u.`id`, u.email, u.nickname, u.`firstname`, u.`lastname`, u.`patronymic`, u.`birthdate`, d.specialization, d.workplace FROM USER u \n" +
            "JOIN doctor d ON u.id = d.id WHERE u.`archive`=0 AND u.`id`=?";
    static final String GET_DOCTORS_QUERIES = "SELECT u.`id`, u.email, u.nickname, u.`firstname`, u.`lastname`, u.`patronymic`, u.`birthdate`, d.specialization, d.workplace FROM USER u \n" +
            "JOIN doctor d ON u.id = d.id WHERE u.`archive`=2";
    static final String GET_PHARMACISTS = "SELECT u.id, u.nickname, u.email, u.firstname, u.lastname, u.patronymic, u.birthdate, u.type\n" +
            "FROM USER u WHERE u.`type`='pharmacist' AND u.`archive`='0'";
    static final String GET_PHARMACISTS_QUERIES = "SELECT u.id, u.nickname, u.email, u.firstname, u.lastname, u.patronymic, u.birthdate, u.type\n" +
            "FROM USER u WHERE u.`type`='pharmacist' AND u.`archive`='2'";
    static final String CONFIRM_USER_QUERY = "UPDATE USER u SET u.`archive`=0 WHERE u.`id`=? ";



    private SQLQueries(){
        throw new IllegalStateException("Utility Class");
    }
}
