package com.epam.pharmacy.service;

import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;

public interface DrugService extends AbstractService{
    CommandResult showAllDrugs (RequestContent requestContent) throws ApplicationException;
}
