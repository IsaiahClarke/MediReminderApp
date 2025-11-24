package com.example.myapplication;

import java.io.Serializable;


public class Medication implements Serializable {

    // Fields
    private String name;
    private String dosage;
    private String frequency;
    private int timerSeconds;


    public Medication(String name, String dosage, String frequency, int timerSeconds) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.timerSeconds = timerSeconds;
    }


    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public int getTimerSeconds() {
        return timerSeconds;
    }

    // Setters (optional, in case you want to update fields later)
    public void setName(String name) {
        this.name = name;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setTimerSeconds(int timerSeconds) {
        this.timerSeconds = timerSeconds;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "name='" + name + '\'' +
                ", dosage='" + dosage + '\'' +
                ", frequency='" + frequency + '\'' +
                ", timerSeconds=" + timerSeconds +
                '}';
    }
}
