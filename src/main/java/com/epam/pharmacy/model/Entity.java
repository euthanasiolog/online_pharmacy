package com.epam.pharmacy.model;

import lombok.*;

import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter @Setter
public abstract class Entity implements Serializable, Cloneable {

    @PositiveOrZero
    private int id;

    private boolean archive;
}
