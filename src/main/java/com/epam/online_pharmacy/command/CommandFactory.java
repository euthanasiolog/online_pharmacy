package com.epam.online_pharmacy.command;

import com.epam.online_pharmacy.util.constant.ProjectConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CommandFactory {
    @Getter
    private static final CommandFactory instance = new CommandFactory();

    public Command getCommand(RequestContent content){
        String name = content.getRequestParameter(ProjectConstant.COMMAND).toUpperCase();
        try {
            CommandType commandType = CommandType.valueOf(name);
            return commandType.getCommand();
        } catch (EnumConstantNotPresentException e){
            return CommandType.START.getCommand();
        }
    }
}
