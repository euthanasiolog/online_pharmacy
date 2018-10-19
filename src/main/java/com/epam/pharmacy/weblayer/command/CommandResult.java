package com.epam.pharmacy.weblayer.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CommandResult {
    private ResponseType responseType;
    private String page;
}
