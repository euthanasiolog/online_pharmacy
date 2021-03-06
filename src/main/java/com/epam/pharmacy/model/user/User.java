package com.epam.pharmacy.model.user;

import com.epam.pharmacy.model.Entity;
import lombok.*;
import javax.validation.constraints.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter @Setter
public class User extends Entity {

    @NotBlank(message = "{notblank}")
    @Size(min = 3, max = 20, message = "{User.nickname.size}")
    @Pattern(regexp = "[a-zA-Zа-яА-ЯёЁўЎіІ']+", message = "{User.nickname.pattern}")
    private String nickName;

    @NotBlank(message = "{notblank}") @Email(message = "{User.email.pattern}")
    private String email;

    @NotBlank(message = "{notblank}") @Size(min = 2, max = 200, message = "{User.firstname.size}")
    @Pattern(regexp = "[a-zA-Zа-яА-ЯёЁўЎіІ']+", message = "{User.firstname.pattern}")
    private String firstName;

    @NotBlank(message = "{notblank}") @Size(min = 2, max = 200, message = "{User.lastname.size}")
    @Pattern(regexp = "[a-zA-Zа-яА-ЯёЁўЎіІ']+", message = "{User.lastname.pattern}")
    private String lastName;

    private String patronymic;

    @NotNull(message = "{notblank}")
    private Date dateOfBirth;

}

