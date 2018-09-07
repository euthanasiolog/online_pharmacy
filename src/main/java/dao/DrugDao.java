package dao;

import entity.Drug;
import entity.Entity;
import entity.entity_enum.Availability;
import entity.entity_enum.DrugForm;
import entity.entity_enum.RecipeType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DrugDao extends AbstractDao {
    private final static String CREATE_SQL = "INSERT INTO drug (name, inn, composition, form, dose, number, shelflife," +
            " price, recipe, availability, ordertime, archive) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    private final static String GET_BY_ID_SQL = "SELECT name, inn, composition, form, dose, number, shelflife, price," +
            " recipe, availability, ordertime, archive" +
            "FROM drud WHERE id=?";
    private final static String UPDATE_SQL = "";
    private final static String DELETE_SQL = "";
    private Drug drug;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    @Override
    public void create(Entity entity) {
        try {
            drug = (Drug)entity;
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_SQL);
            preparedStatement.setString(1, drug.getName());
            if (drug.getInn()!=null){
                preparedStatement.setString(2, drug.getInn());
            }
            if (drug.getComposite()!=null){
                preparedStatement.setString(3, drug.getComposite());
            }
            preparedStatement.setString(4, drug.getForm().getValue());
            if (drug.getDose()!=0){
                preparedStatement.setFloat(5, drug.getDose());
            }
            preparedStatement.setInt(6, drug.getNumber());
            preparedStatement.setDate(7, drug.getShelfLife());
            if (drug.getPrice()!=0){
                preparedStatement.setFloat(8, drug.getPrice());
            }
            preparedStatement.setString(9, drug.getRecipeType().getValue());
            preparedStatement.setString(10, drug.getAvailability().getValue());
            if (drug.getOrderTime()!=0){
                preparedStatement.setInt(11, drug.getOrderTime());
            }
            if (drug.isArchive()){
                preparedStatement.setInt(12, 1);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            super.closeConnection(connection, preparedStatement);
            connection = null;
            preparedStatement = null;
            drug = null;
        }
    }

    @Override
    public Drug getById(int id) {
        try {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement(GET_BY_ID_SQL);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                drug = new Drug();
                drug.setName(resultSet.getString("name"));
                if(resultSet.getString("inn")!=null){
                    drug.setInn(resultSet.getString("inn"));
                }
                if (resultSet.getString("composite")!=null){
                    drug.setComposite(resultSet.getString("composite"));
                }
                try {
                    drug.setForm(DrugForm.valueOf(resultSet.getString("form").toUpperCase()));
                } catch (EnumConstantNotPresentException e){
                    e.printStackTrace();//fix after add logger
                }
                if (resultSet.getFloat("dose")!=0){
                    drug.setDose(resultSet.getFloat("dose"));
                }
                drug.setNumber(resultSet.getInt("number"));
                drug.setShelfLife(resultSet.getDate("shelflife"));
                if (resultSet.getFloat("price")!=0){
                    drug.setPrice(resultSet.getFloat("price"));
                }
                try {
                    drug.setRecipeType(RecipeType.valueOf(resultSet.getString("recipe").toUpperCase()));
                } catch (EnumConstantNotPresentException e){
                    e.printStackTrace();//fix after add logger
                }
                try {
                    drug.setAvailability(Availability.valueOf(resultSet.getString("availability").toUpperCase()));
                } catch (EnumConstantNotPresentException e){
                    e.printStackTrace();//fix after add logger
                }
                if (resultSet.getByte("ordertime")!=0){
                    drug.setOrderTime(resultSet.getByte("ordertime"));
                }
                if (resultSet.getByte("archive")==1){
                    drug.setArchive(true);
                }
                return drug;
            }
        } catch (SQLException e) {
            e.printStackTrace(); //fix after add logger
        } finally {
            super.closeConnection(connection, preparedStatement, resultSet);
            connection = null;
            preparedStatement = null;
            resultSet = null;
            drug = null;
        }
        return null;
    }

    @Override
    public void update(Entity entity) {

    }

    @Override
    public void delete(Entity entity) {

    }
}
