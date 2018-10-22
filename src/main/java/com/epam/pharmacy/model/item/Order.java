package com.epam.pharmacy.model.item;

import com.epam.pharmacy.model.Entity;
import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter @Setter
public class Order extends Entity {
    @NonNull
    private Drug drug;
    @NonNull
    private int number;
    private boolean payment;
}
