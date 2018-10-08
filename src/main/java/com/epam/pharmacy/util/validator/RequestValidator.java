package com.epam.pharmacy.util.validator;

import com.epam.pharmacy.model.Entity;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.web.command.RequestContent;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class RequestValidator {
    public boolean isPasswordValid(RequestContent requestContent){
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

    private Validator getValidator(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        return validatorFactory.getValidator();
    }

    public boolean isEntityValid (Entity entity){
        Validator validator = getValidator();
        Set<ConstraintViolation<Entity>> violations = validator.validate(entity);
        return violations.isEmpty();
    }
}
