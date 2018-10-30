package com.epam.pharmacy.service;

import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;

public interface AdminService extends UserService {

    CommandResult confirmUserRequest (RequestContent requestContent) throws ApplicationException;

    CommandResult deleteUser (RequestContent  requestContent) throws ApplicationException;

}
