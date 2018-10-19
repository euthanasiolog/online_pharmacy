package com.epam.pharmacy.service;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.UserDao;
import com.epam.pharmacy.dao.impl.ClientDaoImpl;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import com.epam.pharmacy.exception.ApplicationException;
import org.apache.logging.log4j.Logger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public interface UserService extends AbstractService{

    CommandResult start(RequestContent requestContent);

    CommandResult signIn (RequestContent requestContent) throws ApplicationException;

    CommandResult registration (RequestContent requestContent);

    default boolean isEmailNotExist (RequestContent requestContent) throws ApplicationException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ValidationMessages",
                (Locale) requestContent.getSessionAtribute(ProjectConstant.LOCALE));
        String email;

            if (requestContent.getRequestParameter("email") != null) {

                email = requestContent.getRequestParameter("email");
                UserDao userDao = new ClientDaoImpl();

                try {
                    if (userDao.isEmailNotExist(email)) {
                        return true;
                    } else {
                        requestContent.insertAttribute("eMailExistError", resourceBundle.getString("email.exist"));
                    }
                } catch (DaoException e) {
                    throw new ApplicationException("error check email", e);
                }
            }
        return false;
    }

    default boolean isNickNameNotExist (RequestContent requestContent) throws ApplicationException {
        String nickName;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ValidationMessages",
                (Locale) requestContent.getSessionAtribute(ProjectConstant.LOCALE));

        if (requestContent.getRequestParameter("nickname") != null) {
            nickName = requestContent.getRequestParameter("nickname");

            UserDao userDao = new ClientDaoImpl();

            try {
                if (userDao.isNickNameNotExist(nickName)){
                    return true;
                } else {
                    requestContent.insertAttribute("nickNameExistError", resourceBundle.getString("nickname.exist"));
                }
            } catch (DaoException e) {
                throw new ApplicationException("error check nickname", e);
            }
        }
        return false;
    }

    default void createUserAttributes(User user, RequestContent requestContent){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ValidationMessages");

        if (requestContent.getRequestParameter("id") != null) {
            int id = Integer.parseInt(requestContent.getRequestParameter("id"));
            user.setId(id);
        }

        if (requestContent.getRequestParameter("nickname") != null) {
            String nickName = requestContent.getRequestParameter("nickname");
            user.setNickName(nickName);
        }

        if (requestContent.getRequestParameter("email") != null) {
            String email = requestContent.getRequestParameter("email");
            user.setEMail(email);
        }

        if (requestContent.getRequestParameter("firstname") != null) {
            String firstName = requestContent.getRequestParameter("firstname");
            user.setFirstName(firstName);
        }

        if (requestContent.getRequestParameter("lastname") != null) {
            String lastName = requestContent.getRequestParameter("lastname");
            user.setLastName(lastName);
        }

        if (requestContent.getRequestParameter("patronymic") != null) {
            String patronymic = requestContent.getRequestParameter("patronymic");
            user.setPatronymic(patronymic);
        }

        if (requestContent.getRequestParameter("year") != null &&
                requestContent.getRequestParameter("month") != null &&
                requestContent.getRequestParameter("day") != null) {

            int year = 0;
            int month = 0;
            int day = 0;

            try {
                year = Integer.parseInt(requestContent.getRequestParameter("year"));
            } catch (NumberFormatException e) {
                requestContent.insertAttribute("yearError", resourceBundle.getString("User.dateofbirth.year"));
            }

            try {
                month = Integer.parseInt(requestContent.getRequestParameter("month"));
            } catch (NumberFormatException e) {
                requestContent.insertAttribute("monthError", resourceBundle.getString("User.dateofbirth.month"));
            }

            try {
                day = Integer.parseInt(requestContent.getRequestParameter("day"));
            } catch (NumberFormatException e) {
                requestContent.insertAttribute("dayError", resourceBundle.getString("User.dateofbirth.day"));
            }

            Date dateOfBirth = null;

            try {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                dateOfBirth = calendar.getTime();
            } catch (IllegalArgumentException e) {
                requestContent.insertAttribute("dateOfBirthError", resourceBundle.getString("User.dateofbirth"));
            }

                user.setDateOfBirth(dateOfBirth);
        }

    }

    default String hashPassword(String pass, Logger log){
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                StringBuilder stringBuilder = new StringBuilder();
                byte[] passBytes = messageDigest.digest(pass.getBytes(StandardCharsets.UTF_8));
                for (int i = 0; i<passBytes.length;i++){
                    stringBuilder.append(passBytes[i]);
                }
                return stringBuilder.toString();
            } catch (NoSuchAlgorithmException e){
                log.error("Can't hash password", e);
                return null;
            }
    }

    default void insertUserSessionAttributes(RequestContent requestContent, User user, Role role){
        requestContent.insertSessionAtribute(ProjectConstant.USER, user);
        requestContent.insertSessionAtribute(ProjectConstant.ROLE, role.toString());
    }
}
