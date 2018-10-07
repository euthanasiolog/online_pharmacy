package com.epam.pharmacy.model;

import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.user.Client;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cart extends Entity {
    @Required
    private Client client;
    private List<Drug> drugsInCart = new LinkedList<>();

    public void addDrugInCart (Drug drug){
        drugsInCart.add(drug);
    }

    public void removeDrugFromCart (Drug drug){
        drugsInCart.remove(drug);
    }

    public List<Drug> getDrugList (){
        return Collections.unmodifiableList(drugsInCart);
    }

}
