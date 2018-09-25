package com.epam.online_pharmacy.command;

@FunctionalInterface
public interface Command {
    CommandResult execute(RequestContent requestContent);
}
