package org.example.hms.classes;

public class Given {
    private String name;
    private Double quantity;
    private int id;
    public Given(String name, Double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {}
    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) {}

}
