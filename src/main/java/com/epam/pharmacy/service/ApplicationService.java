package com.epam.pharmacy.service;

import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;

public interface ApplicationService {
    CommandResult changeLocale(RequestContent requestContent);
    CommandResult refresh (RequestContent requestContent);
    CommandResult goToStart (RequestContent requestContent);
}
