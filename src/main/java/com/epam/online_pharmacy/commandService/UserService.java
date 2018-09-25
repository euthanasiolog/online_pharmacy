package com.epam.online_pharmacy.commandService;

import com.epam.online_pharmacy.command.CommandResult;
import com.epam.online_pharmacy.command.RequestContent;

public interface UserService {
    CommandResult start(RequestContent requestContent);
    CommandResult signIn (RequestContent requestContent);
}
