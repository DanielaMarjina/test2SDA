package com.example.test2sda;

public class Student {
    private String nume;
    private double medie;
    private String localitate;
    public Student(String nume, double media, String localitate) {
        this.nume = nume;
        this.medie = Math.floor(media*100)/100;
        this.localitate = localitate;
    }
    public String getNume() {
        return nume;
    }
    public double getMedie() {
        return medie;
    }
    public String getLocalitate() {
        return localitate;
    }
    @Override
    public String toString() {
        return "\n"+nume + ", " + medie + ", " + localitate;
    }
}