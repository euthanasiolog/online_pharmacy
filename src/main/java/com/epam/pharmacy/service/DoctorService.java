package com.epam.pharmacy.service;


import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.model.user.Doctor;
import com.epam.pharmacy.util.constant.Role;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;

public interface DoctorService extends AbstractService {
    CommandResult confirmRecipe (RequestContent requestContent) throws ApplicationException;
}
