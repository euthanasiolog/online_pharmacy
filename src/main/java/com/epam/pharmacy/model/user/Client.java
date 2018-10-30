package com.epam.pharmacy.model.user;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter @Setter
public class Client extends User{

    @Min(value = 0, message = "{Client.discount.min}")
    @Max(value = 100, message = "{Client.discount.max}")
    private int discount;
}
