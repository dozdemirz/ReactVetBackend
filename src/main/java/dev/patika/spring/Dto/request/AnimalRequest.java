package dev.patika.spring.Dto.request;

import dev.patika.spring.Entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AnimalRequest {
    private Long animalId;
    private String animalName;
    private String animalSpecies;
    private String animalBreed;
    private String animalGender;
    private String animalColor;
    private LocalDate birthDate;
    private Customer customer;
}
