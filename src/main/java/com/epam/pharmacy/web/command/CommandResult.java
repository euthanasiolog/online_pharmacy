package com.epam.pharmacy.web.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandResult {
    private ResponseType responseType;
    private String page;
}
