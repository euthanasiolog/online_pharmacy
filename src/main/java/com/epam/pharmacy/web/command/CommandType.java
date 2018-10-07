package com.epam.pharmacy.web.command;

import com.epam.pharmacy.service.impl.*;

public enum CommandType {
    START(new UserServiceImpl()::start),
    SIGNIN(new UserServiceImpl()::signIn),
    CHANGELOCALE(new ApplicationServiceImpl()::changeLocale),
    REGISTRATION(new UserServiceImpl()::registration),
    REGISTERCLIENT(new ClientServiceImpl()::create),
    REGISTERDOCTOR(new DoctorServiceImpl()::create),
    REGISTERPHARMACIST(new PharmacistServiceImpl()::create);

    private Command command;
    CommandType (Command command){
        this.command = command;
    }
    public Command getCommand(){
        return command;
    }
}
