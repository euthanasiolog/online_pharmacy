package com.epam.pharmacy.command;

import com.epam.pharmacy.commandService.impl.UserServiceImpl;

public enum CommandType {
    START(new UserServiceImpl()::start),
    SIGNIN(new UserServiceImpl()::signIn);
    private Command command;
    CommandType (Command command){
        this.command = command;
    }
    public Command getCommand(){
        return command;
    }
}
