package com.example.myapplication;

import java.io.Serializable;

public class Caretaker implements Serializable {
    private String name;
    private String phone;

    public Caretaker(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return name + " | Phone: " + phone;
    }
}
