package com.epam.pharmacy.util.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
class LoginAndPasswordValidator {
    @NotBlank (message = "{notblank}")
    @Size(min = 5, max = 50, message = "{password.size}")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "{password.pattern}")
    private String password;

    @NotBlank (message = "{notblank}")
    @Size(min = 3, max = 20, message = "{login.size}")
    @Pattern(regexp = "[a-zA-Zа-яА-Я0-9_-]", message = "{login.pattern}")
    private String login;
}
