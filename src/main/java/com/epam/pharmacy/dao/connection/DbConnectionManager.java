package com.epam.pharmacy.dao.connection;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ResourceBundle;

@Log4j2
class DbConnectionManager {
    @Getter
    private int poolSize;
    private String url;
    private String user;
    private String password;
    private static final String USER = "user";
    private static final String URL = "url";
    private static final String PASSWORD = "password";
    private static final String POOL_SIZE = "poolsize";
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("prop.mysqlDB");
    private static final String ADMIN_NICK_NAME = "administrator";
    private static final String ADMIN_PASSWORD = "PharmAdmin1989";
    private static final String ADMIN_EMAIL = "online-pharmacy@emap.com";

    DbConnectionManager(){
            this.url = BUNDLE.getString(URL);
            this.password = BUNDLE.getString(PASSWORD);
            this.user = BUNDLE.getString(USER);
            this.poolSize = new Integer(BUNDLE.getString(POOL_SIZE));

            Connection connection = getConnection();
            PreparedStatement preparedStatement = null;
            PreparedStatement preparedStatement1 = null;
            ResultSet resultSet = null;
            try {
                preparedStatement = connection.prepareStatement("SELECT u.id FROM USER u WHERE u.nickname=? AND u.`password`=SHA2(?,512)");
                preparedStatement.setString(1, ADMIN_NICK_NAME);
                String adminPass = null;
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                    StringBuilder stringBuilder = new StringBuilder();
                    byte[] passBytes = messageDigest.digest(ADMIN_PASSWORD.getBytes(StandardCharsets.UTF_8));
                    for (int i = 0; i<passBytes.length;i++){
                        stringBuilder.append(passBytes[i]);
                    }
                    adminPass = stringBuilder.toString();
                    preparedStatement.setString(2, adminPass);
                } catch (NoSuchAlgorithmException e){
                    log.error("Can't hash password", e);
                }
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    preparedStatement1 = connection.prepareStatement("INSERT INTO USER (nickname, firstname, lastname, PASSWORD, email, birthdate, TYPE ) VALUES (?, 'admin', 'admin', SHA2(?, 512), ? , '2000-11-11', 'admin')");
                    preparedStatement1.setString(1, ADMIN_NICK_NAME);
                    preparedStatement1.setString(2, adminPass);
                    preparedStatement1.setString(3, ADMIN_EMAIL);
                    preparedStatement1.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException("failed check or create admin", e);
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (preparedStatement1 != null) {
                        preparedStatement1.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    log.error("error close connection, etc. after creating admin");
                }
            }
    }

    Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException("DB driver not found!");
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed get connection to DB!");
        }
    }
}
