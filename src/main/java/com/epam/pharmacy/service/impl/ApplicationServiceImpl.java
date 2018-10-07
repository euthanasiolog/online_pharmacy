package com.epam.pharmacy.service.impl;

import com.epam.pharmacy.web.command.CommandResult;
import com.epam.pharmacy.web.command.RequestContent;
import com.epam.pharmacy.web.command.ResponseType;
import com.epam.pharmacy.service.ApplicationService;
import com.epam.pharmacy.util.constant.PagePass;
import com.epam.pharmacy.util.constant.ProjectConstant;

public class ApplicationServiceImpl implements ApplicationService {

    @Override
    public CommandResult changeLocale(RequestContent requestContent) {
        requestContent.insertSessionAtribute(ProjectConstant.LOCALE,
                requestContent.getRequestParameter(ProjectConstant.LOCALE));
        String page = requestContent.getRequestParameter("page");
        if (page!=null){
            return new CommandResult(ResponseType.FORWARD, page);
        }
        return new CommandResult(ResponseType.FORWARD, PagePass.START_PAGE);
    }
}
