package com.epam.pharmacy.web.command;

import com.epam.pharmacy.exception.ApplicationException;

@FunctionalInterface
public interface Command {
    CommandResult execute(RequestContent requestContent) throws ApplicationException;
}
