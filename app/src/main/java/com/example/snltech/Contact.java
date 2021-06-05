package com.example.snltech;

public class Contact {

    private String name;
    private String number;
    private String category;
    private String id;
    public Contact(String name, String number,String category,String id) {
        this.name = name;
        this.number = number;
        this.category=category;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}