package br.com.bitsincloud.clientservice.domain.entity;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    // Método para buscar o enum passando o valor do label
    public static Gender fromString(String value) {
        for (Gender g : Gender.values()) {
            if (g.gender.equalsIgnoreCase(value)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Gênero inválido: " + value);
    }
}
