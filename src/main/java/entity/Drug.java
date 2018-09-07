package entity;

import entity.entity_enum.Availability;
import entity.entity_enum.DrugForm;
import entity.entity_enum.RecipeType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Drug extends Entity {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String inn;
    @Getter @Setter
    private String composition;
    @Getter @Setter
    private DrugForm form;
    @Getter @Setter
    private float dose;
    @Getter @Setter
    private int number;
    @Getter @Setter
    private Date shelfLife;
    @Getter @Setter
    private float price;
    @Getter @Setter
    private RecipeType recipeType;
    @Getter @Setter
    private Availability availability;
    @Getter @Setter
    private byte orderTime;
}
