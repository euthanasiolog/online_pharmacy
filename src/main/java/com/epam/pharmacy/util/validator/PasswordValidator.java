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
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "{password.pattern}")
    private String pass1;

    @NotBlank(message = "{notblank}")
    @Size(min = 5, max = 50, message = "{password.size}")
    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "{password.pattern}")
    private String pass2;

}
