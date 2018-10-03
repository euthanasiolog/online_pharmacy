package com.epam.pharmacy.util.constant;

public final class PagePass {
    public static final String START_PAGE = "pages/startPage.jsp";
    public static final String START_DOCTOR_PAGE = "pages/client/startClientPage.jsp";
    public static final String START_CLIENT_PAGE = "pages/client/startClientPage.jsp";
    public static final String START_PHARMACIST_PAGE = "pages/pharmacist/startPharmacistPage.jsp";

    private PagePass (){
        throw new IllegalStateException("Utility class");
    }
}
