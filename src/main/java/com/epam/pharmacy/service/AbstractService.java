package com.epam.pharmacy.service;

import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.exception.ApplicationException;

public interface AbstractService {
    CommandResult create(RequestContent requestContent) throws ApplicationException;
    CommandResult findById(RequestContent requestContent) throws ApplicationException;
    CommandResult update(RequestContent requestContent) throws ApplicationException;
    CommandResult delete(RequestContent requestContent) throws ApplicationException;

}
