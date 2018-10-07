package com.epam.pharmacy.service;

import com.epam.pharmacy.web.command.CommandResult;
import com.epam.pharmacy.web.command.RequestContent;
import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.constant.Role;
import com.epam.pharmacy.exception.ApplicationException;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Set;

public interface UserService extends AbstractService{

    CommandResult start(RequestContent requestContent);

    CommandResult signIn (RequestContent requestContent) throws ApplicationException;

    CommandResult registration (RequestContent requestContent);

    default void createUserAttributes(User user, RequestContent requestContent){
        @NotBlank @Size(min = 3, max = 20) @Pattern(regexp = "[a-zA-Zа-яА-Я0-9_-]")
        String nickName = requestContent.getRequestParameter("nickname");
        @NotBlank @Email
        String email = requestContent.getRequestParameter("email");
        @NotBlank @Size(min = 2, max = 200) @Pattern(regexp = "[a-zA-Zа-яА-Яа-яА-Я]")
        String firstName = requestContent.getRequestParameter("firstname");
        @NotBlank @Size(min = 2, max = 200) @Pattern(regexp = "[a-zA-Zа-яА-Яа-яА-Я]")
        String lastName = requestContent.getRequestParameter("lastname");
        @NotBlank @Size(min = 2, max = 200) @Pattern(regexp = "[a-zA-Zа-яА-Яа-яА-Я]")
        String patronymic = requestContent.getRequestParameter("patronymic");
        @Positive @Size(min = 1900, max = 2018)
        int year = new Integer(requestContent.getRequestParameter("year"));
        @Positive @Size(min = 1, max = 12)
        int month = new Integer(requestContent.getRequestParameter("month"));
        @Positive @Size(min = 1, max = 31)
        int day = new Integer(requestContent.getRequestParameter("day"));
        Date dateOfBirth = new Date(year, month, day);
        user.setNickName(nickName);
        user.setEMail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        if (patronymic!=null){
            user.setPatronymic(patronymic);
        }
        user.setDateOfBirth(dateOfBirth);
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

    default boolean isPasswordValid(RequestContent requestContent){
        @NotBlank @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
        String pass1 = requestContent.getRequestParameter(ProjectConstant.PASS1);
        @NotBlank @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")
        String pass2 = requestContent.getRequestParameter(ProjectConstant.PASS2);
        if (!pass1.equals(pass2)){
            return false;
        }
        Set<ConstraintViolation<String>> violations = getValidator().validate(pass1);
        return violations.isEmpty();
    }

    default void insertUserSessionAttributes(RequestContent requestContent, User user, Role role){
        requestContent.insertSessionAtribute(ProjectConstant.USER, user);
        requestContent.insertSessionAtribute(ProjectConstant.ROLE, role.toString());
    }
}
