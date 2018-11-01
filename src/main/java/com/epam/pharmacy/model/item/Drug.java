package com.epam.pharmacy.model.item;

import com.epam.pharmacy.model.Entity;
import com.epam.pharmacy.model.recipe.RecipeType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter @Setter
public class Drug extends Entity {

    @NotBlank(message = "{notblank}")
    @Size(min = 3, max = 100, message = "{User.nickname.size}")
    @Pattern(regexp = "[^\\s0-9.,/?!@#$%^&*()<>_+=\":;№{}\\[\\]]+", message = "{User.nickname.pattern}")
    private String name;

    @NotBlank(message = "{notblank}")
    @Size(min = 3, max = 100, message = "{User.nickname.size}")
    @Pattern(regexp = "[a-zA-Z ]+", message = "{User.nickname.pattern}")
    private String inn;

    @NotBlank(message = "{notblank}")
    @Size(min = 3, max = 20, message = "{User.nickname.size}")
    @Pattern(regexp = "[^\\f\\n\\r\\t\\v/?!@#$%^&*()<>_+=№{}\\[\\]]+", message = "{User.nickname.pattern}")
    private String composite;

    @NotBlank
    private DrugForm drugForm;

    private float dose;

    @PositiveOrZero
    private int number;


    private Date shelfLife;

    @PositiveOrZero
    private BigDecimal price;

    private RecipeType recipeType;

    private Availability availability;

    @PositiveOrZero
    private int amount;

    @PositiveOrZero
    private int orderTime;

    private String annotation;

}
