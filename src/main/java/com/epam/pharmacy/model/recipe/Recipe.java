package com.epam.pharmacy.model.recipe;

import com.epam.pharmacy.model.Entity;
import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.user.Client;
import com.epam.pharmacy.model.user.Doctor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter @Setter
public class Recipe extends Entity {

    private int serialNumber;

    private Date from;

    private Date to;

    private  boolean used;

//    private String recipeType;

    private RecipeType type;

    private Client client;

    private Doctor doctor;

    private Drug drug;

}