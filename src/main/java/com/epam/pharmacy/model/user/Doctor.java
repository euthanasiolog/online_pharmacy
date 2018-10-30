package com.epam.pharmacy.model.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter @Setter
public class Doctor extends User{

    @NotBlank(message = "{Doctor.specialization.notblanck}")
    @Size(min = 3, max = 50, message = "{Doctor.specialization.size}")
    private String specialization;

    @NotBlank(message = "{Doctor.workplace.notblanck}")
    @Size(min = 3, max = 200, message = "{Doctor.workplace.size}")
    private String workplace;
}
