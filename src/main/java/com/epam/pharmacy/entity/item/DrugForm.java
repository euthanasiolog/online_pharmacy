package com.epam.pharmacy.entity.item;

public enum DrugForm {
    PILL("pill"),
    SOLUTION("solution"),
    POWDER("powder"),
    CREAM("cream"),
    GEL("gel");
    private String value;
    private DrugForm(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
