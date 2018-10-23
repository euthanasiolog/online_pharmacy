package com.epam.pharmacy.util.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
class PasswordValidator {
    @NotBlank(message = "{notblank}")
    @Size(min = 5, max = 50, message = "{password.size}")
    @Pattern(regexp = "[a-zA-Zа-яА-ЯёЁўЎіІ0-9_-]+", message = "{password.pattern}")
    private String pass1;

    @NotBlank(message = "{notblank}")
    @Size(min = 5, max = 50, message = "{password.size}")
    @Pattern(regexp = "[a-zA-Zа-яА-ЯёЁўЎіІ0-9_-]+", message = "{password.pattern}")
    private String pass2;

}
