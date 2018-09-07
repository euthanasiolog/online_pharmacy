package entity.entity_enum;

public enum Availability {
    STOCK("stock"),
    ORDER("order"),
    NOT("not");
    private String value;
    private Availability(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
