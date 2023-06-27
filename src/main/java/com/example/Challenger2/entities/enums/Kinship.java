package com.example.Challenger2.entities.enums;

public enum Kinship {

    Parents("Parents"),
    Children("Children"),
    Others("Others");

    private String name;
    private Kinship(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
