package dev.patika.spring.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "animals")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Animal {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id", columnDefinition = "serial")
    private Long animalId;

    @Column(name = "animal_name", nullable = false)
    private String animalName;

    @Column(name = "animal_species", nullable = false)
    private String species;

    @Column(name = "animal_breed", nullable = false)
    private String breed;

    @Column(name = "animal_gender", nullable = false)
    private String gender;

    @Column(name = "animal_color")
    private String color;

    @Temporal(TemporalType.DATE)
    @Column(name = "animal_birth")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnore
    @JsonManagedReference
    private List<Vaccine> vaccines;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;


}
