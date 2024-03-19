package dev.patika.spring.Dto.response;

import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Doctor;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentResponse {
    private Long appointmentId;
    private LocalDateTime appointmentDate;
    private Doctor doctor;
    private Animal animal;
}
