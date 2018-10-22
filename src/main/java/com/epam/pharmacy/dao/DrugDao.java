package com.epam.pharmacy.dao;

import com.epam.pharmacy.model.item.Drug;

import java.util.List;

public interface DrugDao extends AbstractDao<Drug> {
    List<Drug> showAllDrugs () throws DaoException;

    List<Drug> searchDrug (String search) throws DaoException;
}
