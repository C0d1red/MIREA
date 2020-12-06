package com.c0d1red.petshop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name="pet")
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    private Long id;
    private String name;
    private String type;
    private BigDecimal cost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(name, pet.name) &&
                type.equals(pet.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
