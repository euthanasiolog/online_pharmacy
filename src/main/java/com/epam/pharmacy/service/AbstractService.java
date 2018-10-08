package com.epam.pharmacy.service;

import com.epam.pharmacy.web.command.CommandResult;
import com.epam.pharmacy.web.command.RequestContent;
import com.epam.pharmacy.model.Entity;
import com.epam.pharmacy.exception.ApplicationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public interface AbstractService {
    CommandResult create(RequestContent requestContent) throws ApplicationException;
    CommandResult findById(RequestContent requestContent);
    CommandResult update(RequestContent requestContent);
    CommandResult delete(RequestContent requestContent);

}
