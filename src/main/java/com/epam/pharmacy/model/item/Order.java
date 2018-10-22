package com.epam.pharmacy.model.item;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter @Setter
public class Order {
    @NonNull
    private Drug drug;
    @NonNull
    private int number;
    private boolean payment;
}
