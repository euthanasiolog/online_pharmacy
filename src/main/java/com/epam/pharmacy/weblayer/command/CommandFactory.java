package com.epam.pharmacy.weblayer.command;

import com.epam.pharmacy.util.constant.ProjectConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
public class CommandFactory {
    @Getter
    private static final CommandFactory instance = new CommandFactory();

    public Command getCommand(RequestContent content){
        if (content.getRequestParameter(ProjectConstant.COMMAND) != null) {
            String name = content.getRequestParameter(ProjectConstant.COMMAND).toUpperCase();
            try {
                CommandType commandType = CommandType.valueOf(name);
                return commandType.getCommand();
            } catch (EnumConstantNotPresentException | IllegalArgumentException e){
                log.error("Can't define command "+"'"+name+"'");
                return CommandType.START.getCommand();
            }
        }
        else return null;
    }
}
