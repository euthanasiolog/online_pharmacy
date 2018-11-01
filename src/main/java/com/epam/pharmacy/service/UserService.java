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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface UserService extends AbstractService {

    CommandResult signIn (RequestContent requestContent) throws ApplicationException;

    CommandResult registration (RequestContent requestContent);

    CommandResult goToCabinet (RequestContent requestContent);

    CommandResult exit (RequestContent requestContent);

    CommandResult endSignIn (User user, RequestContent requestContent, Role role) throws ApplicationException;

    default boolean isEmailNotExist (RequestContent requestContent) throws ApplicationException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ValidationMessages",
                (Locale) requestContent.getSessionAttribute(ProjectConstant.LOCALE));
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
                (Locale) requestContent.getSessionAttribute(ProjectConstant.LOCALE));

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

    default boolean createUserAttributes(User user, RequestContent requestContent) throws ApplicationException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ValidationMessages",
                (Locale) requestContent.getSessionAttribute(ProjectConstant.LOCALE));

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
            user.setEmail(email);
        }

        if (requestContent.getRequestParameter("firstname") != null) {
            String firstName = requestContent.getRequestParameter("firstname");

            user.setFirstName(firstName);
        }

        if (requestContent.getRequestParameter("lastname") != null) {
            String lastName = requestContent.getRequestParameter("lastname");
            user.setLastName(lastName);
        }

        if (requestContent.getRequestParameter(ProjectConstant.PATRONYMIC) != null) {
            String patronymic = requestContent.getRequestParameter(ProjectConstant.PATRONYMIC);
            Pattern p = Pattern.compile("[a-zA-Zа-яА-ЯёЁўЎіІ']+");
            Matcher matcher = p.matcher(requestContent.getRequestParameter(ProjectConstant.PATRONYMIC));
            if (matcher.matches()) {
                if (patronymic.length()<200&&patronymic.length()>1) {
                    user.setPatronymic(patronymic);
                } else {
                    requestContent.insertAttribute("patronymicError", resourceBundle.getString("User.patronymic.size"));
                }
            } else {
                requestContent.insertAttribute("patronymicError", resourceBundle.getString("User.patronymic.pattern"));
            }
        }

        if (requestContent.getRequestParameter(ProjectConstant.DATE_OF_BIRTH) != null) {
            Pattern date = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            Matcher matcher = date.matcher(requestContent.getRequestParameter(ProjectConstant.DATE_OF_BIRTH));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (matcher.matches()) {
                try {
                    Date dateOfBirth = dateFormat.parse(requestContent.getRequestParameter(ProjectConstant.DATE_OF_BIRTH));
                    user.setDateOfBirth(dateOfBirth);
                } catch (ParseException e) {
                    throw new ApplicationException("error parse Date", e);
                }
            } else {
                requestContent.insertAttribute("dateOfBirthError", resourceBundle.getString("date.wrong.pattern"));
                return false;
            }
        }
        return true;
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
        requestContent.insertSessionAttribute(ProjectConstant.USER, user);
        requestContent.insertSessionAttribute(ProjectConstant.ROLE, role);
    }
}
