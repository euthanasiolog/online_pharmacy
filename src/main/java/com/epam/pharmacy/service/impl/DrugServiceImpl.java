package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.DrugDao;
import com.epam.pharmacy.dao.impl.DrugDaoImpl;
import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.service.DrugService;
import com.epam.pharmacy.weblayer.command.ResponseType;

import java.util.List;

public class DrugServiceImpl implements DrugService {
    @Override
    public CommandResult create(RequestContent requestContent) {
        return null;
    }

    @Override
    public CommandResult findById(RequestContent requestContent) {
        return null;
    }

    @Override
    public CommandResult update(RequestContent requestContent) {
        return null;
    }

    @Override
    public CommandResult delete(RequestContent requestContent) {
        return null;
    }

    @Override
    public CommandResult showAllDrugs(RequestContent requestContent) throws ApplicationException {
        DrugDao drugDao = new DrugDaoImpl();
        try {
            List<Drug> drugs = drugDao.showAllDrugs();
            requestContent.insertSessionAttribute(ProjectConstant.DRUGS, drugs);
        } catch (DaoException e) {
            throw new ApplicationException("error show all drugs", e);
        }
        return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
    }

    @Override
    public CommandResult searchDrug(RequestContent requestContent) throws ApplicationException {
        DrugDao drugDao = new DrugDaoImpl();
        try {
            List<Drug> drugs = drugDao.searchDrug(requestContent.getRequestParameter(ProjectConstant.DRUG_SEARCH));
            requestContent.insertSessionAttribute(ProjectConstant.DRUGS, drugs);
        } catch (DaoException e) {
            throw new ApplicationException("error show all drugs", e);
        }
        return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
    }


}
