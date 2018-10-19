package com.epam.pharmacy.util.constant;

import java.util.Locale;

public class LocaleFactory {

    public static Locale getLocale (String locale) {
        AppLocale appLocale = AppLocale.valueOf(locale.toUpperCase());
        return getLocale(appLocale);
    }

    public static Locale getLocale (AppLocale locale) {
        return getAppLocale(locale);
    }

    private static Locale getAppLocale(AppLocale locale) {
        switch (locale) {
            case BE:
                return new Locale(ProjectConstant.BY_LOCALE_LANG, ProjectConstant.BY_LOCALE_COUNTRY);
            case EN:
                return new Locale(ProjectConstant.EN_LOCALE_LANG, ProjectConstant.EN_LOCALE_COUNTRY);
            case RU:
                return new Locale(ProjectConstant.RU_LOCALE_LANG, ProjectConstant.RU_LOCALE_COUNTRY);
            default:
                return Locale.getDefault();
        }
    }
}
