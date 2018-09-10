package com.epam.online_pharmacy.entity;

public enum  RecipeType {
    WITHOUT("without"),
    REGULAR("regular"),
    NARCOTIC("narcotic");
    private String value;
    private RecipeType(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
