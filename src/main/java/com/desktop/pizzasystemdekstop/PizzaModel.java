package com.desktop.pizzasystemdekstop;

public class PizzaModel {
    private Integer id;

    private String name;

    private String dough;

    private String sauce;

    private String size;


    private Integer price;

    public PizzaModel() {
    }

    public PizzaModel(Integer id, String name, String dough, String sauce, String size, Integer price) {
        this.id = id;
        this.name = name;
        this.dough = dough;
        this.sauce = sauce;
        this.size = size;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDough() {
        return dough;
    }

    public void setDough(String dough) {
        this.dough = dough;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
