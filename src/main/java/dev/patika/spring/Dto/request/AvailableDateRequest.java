package dev.patika.spring.Dto.request;

import dev.patika.spring.Entities.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvailableDateRequest {
    private long availableDateId;
    private LocalDate availableDate;
    private Doctor doctor;
}
