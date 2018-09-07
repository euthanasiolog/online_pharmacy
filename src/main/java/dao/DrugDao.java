package dao;

import entity.Drug;
import entity.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DrugDao extends AbstractDao {
    private final static String CREATE_SQL = "INSERT INTO drug (name, inn, composition, form, dose, number, shelflife, price, recipe, availability, ordertime, archive) \n" +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private Drug drug;
    private Connection connection;
    @Override
    public void create(Entity entity) {
        drug = (Drug)entity;
        connection = super.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SQL);
            preparedStatement.setString(1, drug.getName());
            if (drug.getInn()!=null){
                preparedStatement.setString(2, drug.getInn());
            }
            if (drug.getComposition()!=null){
                preparedStatement.setString(3, drug.getComposition());
            }
            preparedStatement.setString(4, drug.getForm().getValue());
            if (drug.getDose()!=0){
                preparedStatement.setFloat(5, drug.getDose());
            }
            preparedStatement.setInt(6, drug.getNumber());
            preparedStatement.setDate(7, drug.getShelfLife());
            preparedStatement.setFloat(8, drug.getPrice());
            preparedStatement.setString(9, drug.getRecipeType().getValue());
            preparedStatement.setString(10, drug.getAvailability().getValue());
            if (drug.getOrderTime()!=0){
                preparedStatement.setInt(11, drug.getOrderTime());
            }
            if (drug.isArchive()){
                preparedStatement.setInt(12, 1);
            }
            preparedStatement.execute();
            super.closeConnection(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getById(long id) {

    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void delete(Entity entity) {

    }
}
