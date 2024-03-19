package dev.patika.spring.Dto.request;

import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentRequest {

    private Long appointmentId;
    private LocalDateTime appointmentDate;
    private Doctor doctor;
    private Animal animal;
}

