package dev.patika.spring.Dto.request;

import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VaccineRequest {
    private long vaccineId;
    private String vaccineName;
    private String vaccineCode;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
    private Animal animal;
    private Report report;
}
