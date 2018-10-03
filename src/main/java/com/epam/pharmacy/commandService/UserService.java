package com.epam.pharmacy.commandService;

import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.RequestContent;

public interface UserService extends AbstractService{
    CommandResult start(RequestContent requestContent);
    CommandResult signIn (RequestContent requestContent);
}
