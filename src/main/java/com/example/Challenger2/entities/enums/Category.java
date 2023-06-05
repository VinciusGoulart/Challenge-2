package com.example.Challenger2.entities.enums;

import java.util.ArrayList;
import java.util.List;

public enum Category {
    Alimentacao,
    Saude,
    Moradia,
    Transporte,
    Educacao,
    Lazer,
    Imprevistos,
    Outras;

    public static List<String> getCategories() {
        List<String> list = new ArrayList<>();
        for (Category value : Category.values()) {
            list.add(value.name());
        }
        return list;
    }

}
