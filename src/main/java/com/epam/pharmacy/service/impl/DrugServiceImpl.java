package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.dao.DaoException;
import com.epam.pharmacy.dao.DrugDao;
import com.epam.pharmacy.dao.impl.DrugDaoImpl;
import com.epam.pharmacy.exception.ApplicationException;
import com.epam.pharmacy.model.item.Availability;
import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.item.DrugForm;
import com.epam.pharmacy.model.recipe.RecipeType;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;
import com.epam.pharmacy.util.validator.RequestValidator;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.service.DrugService;
import com.epam.pharmacy.weblayer.command.ResponseType;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class DrugServiceImpl implements DrugService {
    @Override
    public CommandResult create(RequestContent requestContent) throws ApplicationException {
        RequestValidator requestValidator = new RequestValidator();
        Drug drug = createDrugAttributes(requestContent);
        if (requestValidator.isEntityValid(drug, requestContent)) {
            DrugDao drugDao = new DrugDaoImpl();
            try {
                drugDao.create(drug, null);
                return new CommandResult(ResponseType.FORWARD, PagePath.ADD_DRUG);
            } catch (DaoException e) {
                log.error("error create drug", e);
                throw new ApplicationException("error create drug", e);
            }
        }
        return new CommandResult(ResponseType.FORWARD, PagePath.ADD_DRUG);
    }

    @Override
    public CommandResult findById(RequestContent requestContent) throws ApplicationException {
        int id = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ID));

        DrugDao drugDao = new DrugDaoImpl();
        try {
            Drug drug = drugDao.findById(id);
            requestContent.insertSessionAttribute(ProjectConstant.DRUG, drug);
        } catch (DaoException e) {
            log.error("error find drug by id", e);
            throw new ApplicationException("error find drug by id", e);
        }
        return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
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

    private Drug createDrugAttributes (RequestContent requestContent) throws ApplicationException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ValidationMessages",
                (Locale) requestContent.getSessionAttribute(ProjectConstant.LOCALE));

        Drug drug = new Drug();

        String name = requestContent.getRequestParameter(ProjectConstant.NAME);
        if (name != null) {
            drug.setName(name);
        }
        String inn = requestContent.getRequestParameter(ProjectConstant.INN);
        if (inn != null) {
            drug.setInn(inn);
        }
        String composite = requestContent.getRequestParameter(ProjectConstant.COMPOSITE);
        if (composite != null) {
            drug.setComposite(composite);
        }
        try {
             DrugForm drugForm = DrugForm.valueOf(requestContent.getRequestParameter(ProjectConstant.DRUG_FORM).toUpperCase());
             drug.setDrugForm(drugForm);
        } catch (IllegalArgumentException e) {
             log.error("illegal drug form");
        }

        if (requestContent.getRequestParameter(ProjectConstant.DOSE) != null) {
            float dose = Float.parseFloat(requestContent.getRequestParameter(ProjectConstant.DOSE));
            if (dose != 0) {
                drug.setDose(dose);
            }
        }
        int number = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.NUMBER));
        drug.setNumber(number);

        if (requestContent.getRequestParameter(ProjectConstant.SHELFLIFE) != null) {
            Pattern date = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            Matcher matcher = date.matcher(requestContent.getRequestParameter(ProjectConstant.SHELFLIFE));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (matcher.matches()) {
                try {
                    Date shelfLife = dateFormat.parse(requestContent.getRequestParameter(ProjectConstant.SHELFLIFE));
                    drug.setShelfLife(shelfLife);
                } catch (ParseException e) {
                    throw new ApplicationException("error parse Date", e);
                }
            } else {
                requestContent.insertAttribute("shelfLifeError", resourceBundle.getString("date.wrong.pattern"));
            }
        }

        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(requestContent.getRequestParameter(ProjectConstant.PRICE)));
        drug.setPrice(price);

        try {
            RecipeType recipeType = RecipeType.valueOf(requestContent.getRequestParameter(ProjectConstant.RECIPE_TYPE).toUpperCase());
            drug.setRecipeType(recipeType);
        } catch (IllegalArgumentException e) {
            log.error("illegal recipe type");
        }

        try {
            Availability availability = Availability.valueOf(requestContent.getRequestParameter(ProjectConstant.AVAILABILITY).toUpperCase());
            drug.setAvailability(availability);
        } catch (IllegalArgumentException e) {
            log.error("illegal availability type");
        }

        int amount = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.AMOUNT));
        drug.setAmount(amount);

        if (requestContent.getRequestParameter(ProjectConstant.ORDER_TIME) != "") {
            int orderTime = Integer.parseInt(requestContent.getRequestParameter(ProjectConstant.ORDER_TIME));
            drug.setOrderTime(orderTime);
        }

        String annotation = requestContent.getRequestParameter(ProjectConstant.ANNOTATION);
        if (annotation != null) {
            drug.setAnnotation(annotation);
        }

        return drug;
    }

}
