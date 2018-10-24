package com.epam.pharmacy.model.item;

import com.epam.pharmacy.model.Entity;
import lombok.*;

@NoArgsConstructor
@EqualsAndHashCode
@Getter @Setter
public class Order extends Entity {
    private Drug drug;
    private int number;
    private boolean payment;
}
