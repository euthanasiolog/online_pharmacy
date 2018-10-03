package com.epam.pharmacy.command;

@FunctionalInterface
public interface Command {
    CommandResult execute(RequestContent requestContent);
}
