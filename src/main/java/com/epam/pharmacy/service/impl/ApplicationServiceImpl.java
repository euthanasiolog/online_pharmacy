package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.util.constant.LocaleFactory;
import com.epam.pharmacy.weblayer.command.CommandResult;
import com.epam.pharmacy.weblayer.command.RequestContent;
import com.epam.pharmacy.weblayer.command.ResponseType;
import com.epam.pharmacy.service.ApplicationService;
import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;

public class ApplicationServiceImpl implements ApplicationService {

    @Override
    public CommandResult changeLocale(RequestContent requestContent) {

        requestContent.insertSessionAtribute(ProjectConstant.LOCALE,
                LocaleFactory.getLocale(requestContent.getRequestParameter(ProjectConstant.LOCALE)));

        String page = requestContent.getRequestParameter(ProjectConstant.PAGE);

        if (page!=null){
            return new CommandResult(ResponseType.FORWARD, page);
        }

        return new CommandResult(ResponseType.FORWARD, PagePath.START_PAGE);
    }

    @Override
    public CommandResult refresh(RequestContent requestContent) {
        return new CommandResult(ResponseType.FORWARD, requestContent.getRequestParameter(ProjectConstant.PAGE));
    }
}
