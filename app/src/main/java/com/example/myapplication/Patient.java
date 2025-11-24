package com.example.myapplication;

import java.io.Serializable;

public class Patient implements Serializable {
    private String name;
    private String age;
    private String condition;

    public Patient(String name, String age, String condition) {
        this.name = name;
        this.age = age;
        this.condition = condition;
    }

    public String getName() { return name; }
    public String getAge() { return age; }
    public String getCondition() { return condition; }

    @Override
    public String toString() {
        return name + " | Age: " + age + " | Condition: " + condition;
    }
}
