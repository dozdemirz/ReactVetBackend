package dev.patika.spring.Dto.response;

import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Report;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VaccineResponse {

    private Animal animal;
    private long vaccineId;
    private String vaccineName;
    private String vaccineCode;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
    private Report report;

    private boolean isProtectionExpired;
}
