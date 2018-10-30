package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.*;
import com.epam.pharmacy.dao.impl.AdminDaoImpl;
import com.epam.pharmacy.dao.impl.DoctorDaoImpl;
import com.epam.pharmacy.dao.impl.PharmacistDaoImpl;
import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.service.AdminService;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.weblayer.command.ResponseType;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class AdminServiceImpl extends UserServiceImpl implements AdminService {

    @Override
    public CommandResult endSignIn(User user, RequestContent requestContent, Role role) throws ApplicationException {
        DoctorDao doctorDao = new DoctorDaoImpl();
        PharmacistDao pharmacistDao = new PharmacistDaoImpl();

        List<Doctor> doctorsQueries = new ArrayList<>();
        List<User> pharmacistQueries = new ArrayList<>();
        try {
            doctorsQueries = doctorDao.getDoctorsQueries();
            pharmacistQueries = pharmacistDao.getPharmacistQueries();
        } catch (DaoException e) {
            log.error("error get registration queries", e);
            throw new ApplicationException("error get registration queries", e);
        }

        requestContent.insertSessionAttribute(ProjectConstant.DOCTOR_QUERIES, doctorsQueries);
        requestContent.insertSessionAttribute(ProjectConstant.PHARMACIST_QUERIES, pharmacistQueries);

        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, PagePath.ADMIN_CABINET);
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }


    @Override
    public CommandResult confirmUserRequest(RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));

        AdminDao adminDao = new AdminDaoImpl();
        try {
            adminDao.confirmUserRegistrationQuery(id);
        } catch (DaoException e) {
            log.error("error confirm user request", e);
            throw new ApplicationException("error confirm user request", e);
        }

        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }

    @Override
    public CommandResult deleteUser(RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));

        AdminDao adminDao = new AdminDaoImpl();
        try {
            adminDao.deleteUser(id);
        } catch (DaoException e) {
            log.error("error delete user", e);
            throw new ApplicationException("error delete user", e);
        }

        requestContent.insertAttribute(ProjectConstant.REDIRECT_PATH, requestContent.getRequestParameter(ProjectConstant.PAGE));
        return new CommandResult(ResponseType.FORWARD, PagePath.PRG_PAGE);
    }
}
