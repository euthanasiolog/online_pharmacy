package com.epam.pharmacy.service;

import com.epam.pharmacy.model.user.User;
import com.epam.pharmacy.util.constant.AppLocale;
import com.epam.pharmacy.util.constant.LocaleFactory;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.*;

@Log4j2
public class UserServiceTest {

    @Test
    public void setDateOfBirthTest () {
        int year = 18;
        int day = 30;
        int month = 5;
        Date dateOfBirth;

        Calendar calendar = Calendar.getInstance();
        calendar.set(1900+year, month, day);
        dateOfBirth = calendar.getTime();

        User user = new User();
        user.setDateOfBirth(dateOfBirth);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, LocaleFactory.getLocale(AppLocale.RU));

        System.out.println(dateFormat.format(dateOfBirth));

        assertEquals(user.getDateOfBirth(), dateOfBirth);

    }

}