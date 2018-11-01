package com.epam.pharmacy.util.constant;

public final class PagePath {
    public static final String INDEX = "index.jsp";
    public static final String START_PAGE = "pages/startPage.jsp";
    public static final String REGISTRATION_CLIENT_PAGE = "pages/client/clientRegistrationPage.jsp";
    public static final String REGISTRATION_DOCTOR_PAGE = "pages/doctor/doctorRegistrationPage.jsp";
    public static final String REGISTRATION_PHARMACIST_PAGE = "pages/pharmacist/pharmacistRegistrationPage.jsp";
    public static final String PROFILE_CLIENT_PAGE = "pages/client/clientProfilePage.jsp";
    public static final String PROFILE_DOCTOR_PAGE = "pages/doctor/doctorProfilePage.jsp";
    public static final String PROFILE_PHARMACIST_PAGE = "pages/pharmacist/pharmacistProfilePage.jsp";
    public static final String CABINET_CLIENT_PAGE = "pages/client/clientCabinetPage.jsp";
    public static final String CABINET_DOCTOR_PAGE = "pages/doctor/doctorCabinetPage.jsp";
    public static final String CABINET_PHARMACIST_PAGE = "pages/pharmacist/pharmacistCabinetPage.jsp";
    public static final String REGISTRATION_SUCCESSFUL_PAGE = "pages/registrationSuccessfulPage.jsp";
    public static final String REGISTRATION_CONFIRM_PAGE = "pages/registrationConfirm.jsp";
    public static final String PRG_PAGE = "pages/prg.jsp";
    public static final String PAYMENT = "pages/client/payment.jsp";
    public static final String ADD_DRUG = "pages/pharmacist/drugAdd.jsp";
    public static final String RECIPE_REQUEST_CONFIRM = "pages/client/recipeRequestConfirm.jsp";
    public static final String ADMIN_CABINET = "pages/admin/adminCabinet.jsp";
    public static final String MANAGE_DRUG = "pages/pharmacist/manageDrug.jsp";

    private PagePath(){
        throw new IllegalStateException("Utility class");
    }
}
