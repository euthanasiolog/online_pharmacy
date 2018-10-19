package com.epam.pharmacy.util.validator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

import javax.validation.MessageInterpolator.Context;
import java.util.Locale;

public class ContextualMessageInterpolator extends ResourceBundleMessageInterpolator {

    private Locale locale;

    public ContextualMessageInterpolator (Locale locale){
        this.locale = locale;
    }

    @Override
    public String interpolate(String message, Context context) {
        if (locale!=null){
            return super.interpolate(message, context, locale);
        }
        return super.interpolate(message, context);
    }

    @Override
    public String interpolate(String message, Context context, Locale locale) {
        if (locale!=null){
            return super.interpolate(message, context, this.locale);
        }
        return super.interpolate(message, context, locale);
    }
}
