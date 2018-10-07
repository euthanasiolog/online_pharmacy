package com.epam.pharmacy.service;

import com.epam.pharmacy.web.command.CommandResult;
import com.epam.pharmacy.web.command.RequestContent;

public interface ApplicationService {
    CommandResult changeLocale(RequestContent requestContent);
}
