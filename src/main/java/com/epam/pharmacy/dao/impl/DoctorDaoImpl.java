package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.DoctorDao;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import lombok.extern.log4j.Log4j2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class DoctorDaoImpl implements DoctorDao {

    @Override
    public boolean create(Doctor doctor, String password) throws DaoException {

        boolean b;
        Connection connection = startTransaction();
        try (PreparedStatement preparedStatementUser = connection.prepareStatement(SQLQueries.CREATE_USER_QUERY);
             PreparedStatement preparedStatementDoctor =  connection.prepareStatement(SQLQueries.CREATE_DOCTOR)) {

            List<Object> userParams = createUserAttributes(doctor, password, Role.DOCTOR);
            setQueryParams(userParams, preparedStatementUser);
            preparedStatementUser.executeUpdate();

            List<Object> doctorParams = putParameters(doctor.getSpecialization(), doctor.getWorkplace());
            setQueryParams(doctorParams, preparedStatementDoctor);
            preparedStatementDoctor.executeUpdate();
            b = commitTransaction(connection);

        } catch (SQLException e) {
            log.error("error create doctor", e);
            throw new DaoException("error create doctor", e);
        }
        return b;
    }

    @Override
    public Doctor signIn(String login, String password) throws DaoException{
        ResultSetWrapper resultSetUser = signInUser(login, password, SQLQueries.USER_SIGN_IN);

        if (!resultSetUser.isEmpty()) {
            Map<String, Object> result = resultSetUser.getResult().get(0);
            List<Object> doctorParams = putParameters(result.get("id"));

            ResultSetWrapper resultSetDoctor = executeQueryResult(SQLQueries.FIND_DOCTOR_BY_ID, doctorParams);
            return getDoctor(resultSetDoctor.getResult().get(0));
        }
        return null;
    }

    @Override
    public Doctor findById(int id) throws DaoException {
        List<Object> param = putParameters(id);

        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.FIND_DOCTOR_BY_ID, param);
        if (!resultSet.isEmpty()){
            return getDoctor(resultSet.getResult().get(0));
        }
        return null;
    }

    @Override
    public boolean update(Doctor doctor) {
        return false;
    }

    @Override
    public boolean delete(Doctor entity) throws DaoException {
        return false;
    }

    @Override
    public List<Doctor> getDoctors() throws DaoException {
        List<Doctor> doctors = new ArrayList<>();

        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.GET_DOCTORS);
        for (Map<String, Object> result : resultSet.getResult()) {
            Doctor doctor = getDoctor(result);
            doctors.add(doctor);
        }
        return doctors;
    }

    @Override
    public List<Doctor> getDoctorsQueries() throws DaoException {
        List<Doctor> doctors = new ArrayList<>();

        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.GET_DOCTORS_QUERIES);
        if (!resultSet.isEmpty()) {
            for (Map<String, Object> res : resultSet.getResult()) {
                Doctor doctor = getDoctor(res);
                doctors.add(doctor);
            }
        }
        return doctors;
    }

    private Doctor getDoctor(Map<String, Object> resultSet) {
        if (!resultSet.isEmpty()){
            Doctor doctor = new Doctor();
            readUserAttributes(doctor, resultSet);
            readDoctorAttributes(doctor, resultSet);
            return doctor;
        }
        return null;
    }

    private void readDoctorAttributes (Doctor doctor, Map <String, Object> result) {
                doctor.setSpecialization((String) result.get("specialization"));
                doctor.setWorkplace((String) result.get("workplace"));
    }

}
