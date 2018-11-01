package com.epam.pharmacy.dao.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.DrugDao;
import com.epam.pharmacy.dao.connection.ResultSetWrapper;
import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.item.Availability;
import com.epam.pharmacy.model.item.DrugForm;
import com.epam.pharmacy.model.recipe.RecipeType;
import com.epam.pharmacy.util.constant.ProjectConstant;
import lombok.extern.log4j.Log4j2;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

@Log4j2
public class DrugDaoImpl implements DrugDao {

    @Override
    public boolean create(Drug drug, String s) throws DaoException {
        List<Object> params = createDrugParams(drug);
        return executeQueryUpdate(SQLQueries.CREATE_DRUG, params);
    }

    @Override
    public Drug findById(int id) throws DaoException {
        List<Object> params = new ArrayList<>();
        params.add(id);

        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.FIND_DRUG_BY_ID, params);

        if (!resultSet.isEmpty()) {
            return readDrugAttributes(resultSet.getResult().get(0));
        }
        return null;
    }

    @Override
    public boolean update(Drug drug) throws DaoException {
        List<Object> params = createDrugParams(drug);
        params.add(drug.getId());

        return executeQueryUpdate(SQLQueries.UPDATE_DRUG, params);
    }

    @Override
    public boolean delete(Drug drug) throws DaoException {
        List<Object> params = putParameters(drug.getId());

        return executeQueryUpdate(SQLQueries.DELETE_DRUG, params);
    }

    @Override
    public List<Drug> showAllDrugs() throws DaoException {
        List<Drug> drugs = new ArrayList<>();

        ResultSetWrapper resultSet = executeQueryResult(SQLQueries.SHOW_ALL_DRUGS);
        if (!resultSet.isEmpty()) {
            for (Map<String, Object> res : resultSet.getResult()) {
                drugs.add(readDrugAttributes(res));
            }
        }
        return drugs;
    }

    @Override
    public List<Drug> searchDrug(String search) throws DaoException {
        search = search.replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "");
        log.debug("search query = " + search);
        String query = SQLQueries.SEARCH_DRUG_1 + search + SQLQueries.SEARCH_DRUG_2 + search + SQLQueries.SEARCH_DRUG_3;
        ResultSetWrapper resultSet = executeQueryResult(query);
        List<Drug> drugs = new ArrayList<>();
        if (!resultSet.isEmpty()) {
            for (Map<String, Object> res : resultSet.getResult()) {
                drugs.add(readDrugAttributes(res));
            }
        }
        return drugs;
    }

    private Drug readDrugAttributes(Map<String, Object> resultSet) {
        Drug drug = new Drug();
        drug.setId((int) resultSet.get(ProjectConstant.ID));
        drug.setName((String) resultSet.get(ProjectConstant.NAME));
        if (resultSet.get(ProjectConstant.INN) != null) {
            drug.setInn((String) resultSet.get(ProjectConstant.INN));
        }
        if (resultSet.get(ProjectConstant.COMPOSITE) != null) {
            drug.setComposite((String) resultSet.get(ProjectConstant.COMPOSITE));
        }
        try {
            drug.setDrugForm(DrugForm.valueOf((String) resultSet.get(ProjectConstant.FORM)));
        } catch (EnumConstantNotPresentException e) {
            log.error("No enum for drug form " + resultSet.get(ProjectConstant.FORM), e);
        }
        if (resultSet.get(ProjectConstant.DOSE) != null) {
            drug.setDose((float) resultSet.get(ProjectConstant.DOSE));
        }
        drug.setNumber((int) resultSet.get(ProjectConstant.NUMBER));
        drug.setShelfLife((Date) resultSet.get(ProjectConstant.SHELFLIFE));
        if (resultSet.get(ProjectConstant.PRICE) != null) {
            drug.setPrice(BigDecimal.valueOf((float) resultSet.get(ProjectConstant.PRICE)));
        }
        try {
            drug.setRecipeType(RecipeType.valueOf(((String) resultSet.get(ProjectConstant.RECIPE)).toUpperCase()));
        } catch (EnumConstantNotPresentException e) {
            log.error("No enum for drug form " + resultSet.get(ProjectConstant.RECIPE), e);
        }
        try {
            drug.setAvailability(Availability.valueOf(((String) resultSet.get(ProjectConstant.AVAILABILITY)).toUpperCase()));
        } catch (EnumConstantNotPresentException e) {
            log.error("No enum for drug form " + resultSet.get(ProjectConstant.AVAILABILITY), e);
        }
        drug.setAmount((int) resultSet.get(ProjectConstant.AMOUNT));
        if (resultSet.get(ProjectConstant.ORDER_TIME) != null) {
            drug.setOrderTime((int) resultSet.get(ProjectConstant.ORDER_TIME));
        }
        if (resultSet.get(ProjectConstant.ARCHIVE) != null) {
            drug.setArchive(true);
        }
        if (resultSet.get(ProjectConstant.ANNOTATION) != null) {
            drug.setAnnotation((String) resultSet.get(ProjectConstant.ANNOTATION));
        }
        return drug;
    }

    private List<Object> createDrugParams(Drug drug) {
        String name = drug.getName();
        String inn = drug.getInn();
        String composite = drug.getComposite();
        String form = drug.getDrugForm().toString().toLowerCase();
        float dose = drug.getDose();
        int number = drug.getNumber();
        Date shelflife = drug.getShelfLife();
        float price = drug.getPrice().floatValue();
        String recipeType = drug.getRecipeType().toString().toLowerCase();
        String availability = drug.getAvailability().toString().toLowerCase();
        int orderTime = drug.getOrderTime();
        int archive = 0;
        if (drug.isArchive()) {
            archive = 1;
        }
        String annotation = drug.getAnnotation();

        return putParameters(name, inn, composite, form, dose, number, shelflife, price, recipeType, availability,
                orderTime, archive, annotation);
    }
}
