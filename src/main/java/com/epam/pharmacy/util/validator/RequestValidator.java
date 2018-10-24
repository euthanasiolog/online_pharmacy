package com.epam.pharmacy.util.validator;

import com.epam.pharmacy.model.Entity;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.weblayer.command.RequestContent;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Locale;
import java.util.Set;

@Log4j2
@NoArgsConstructor
public class RequestValidator {

    public boolean isRegistrationPasswordsValid (RequestContent requestContent){

        String pass1 = requestContent.getRequestParameter(ProjectConstant.PASS1);

        String pass2 = requestContent.getRequestParameter(ProjectConstant.PASS2);

        if (pass1 != null && pass2 != null && !pass1.equals(pass2)){
            requestContent.insertAttribute("PassEqualsError", "password 1 don't equals password 2");
            log.error("password 1 don't equals password 2");
            return false;
        }

        PasswordValidator passwordValidator = new PasswordValidator(pass1, pass2);

        Set<ConstraintViolation<PasswordValidator>> violations = getValidator(requestContent).validate(passwordValidator);

        for (ConstraintViolation<PasswordValidator> violation : violations){
            requestContent.insertAttribute(violation.getPropertyPath()+"Error", violation.getMessage());
            log.error(violation.getPropertyPath()+" '"+violation.getInvalidValue()+"' is not correct value, "+
                    violation.getMessage());
        }

        return violations.isEmpty();
    }

    public boolean isPasswordAndLoginValid (RequestContent requestContent) {

        String password = requestContent.getRequestParameter(ProjectConstant.PASSWORD);

        String login = requestContent.getRequestParameter(ProjectConstant.LOGIN);

        LoginAndPasswordValidator validator = new LoginAndPasswordValidator(password, login);

        Set<ConstraintViolation<LoginAndPasswordValidator>> violations = getValidator(requestContent).validate(validator);

        for (ConstraintViolation<LoginAndPasswordValidator> violation : violations){
            requestContent.insertAttribute(violation.getPropertyPath()+"Error", violation.getMessage());
            log.error(violation.getPropertyPath()+" '"+violation.getInvalidValue()+"' is not correct value, "+
                    violation.getMessage());
        }

        return violations.isEmpty();
    }

    private Validator getValidator(RequestContent requestContent){
        ContextualMessageInterpolator contextualMessageInterpolator =
                new ContextualMessageInterpolator((Locale) requestContent.getSessionAtribute(ProjectConstant.LOCALE));

        ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure().
                messageInterpolator(contextualMessageInterpolator).buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    public boolean isEntityValid (Entity entity, RequestContent requestContent){
        Validator validator = getValidator(requestContent);
        Set<ConstraintViolation<Entity>> violations = validator.validate(entity);

        for (ConstraintViolation<Entity> violation : violations){
            requestContent.insertAttribute(violation.getPropertyPath()+"Error", violation.getMessage());
            log.error(violation.getPropertyPath()+" '"+violation.getInvalidValue()+"' is not correct value, "+
                    violation.getMessage());
        }

        return violations.isEmpty();
    }
}
