package entity;

import entity.entity_enum.RecipeType;
import entity.user.Client;
import entity.user.Doctor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Recipe extends Entity {
    @Getter @Setter
    private int serialNumber;
    @Getter @Setter
    private LocalDate from;
    @Getter @Setter
    private LocalDate to;
    @Getter @Setter
    private  boolean used;
    @Getter @Setter
    private RecipeType type;
    @Getter @Setter
    private Client client;
    @Getter @Setter
    private Doctor doctor;
    @Getter @Setter
    private Drug drug;
}
