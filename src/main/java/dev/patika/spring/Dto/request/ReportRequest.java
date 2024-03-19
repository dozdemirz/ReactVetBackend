package dev.patika.spring.Dto.request;
import dev.patika.spring.Entities.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportRequest {
    private long reportId;
    private String reportTitle;
    private String reportDiagnosis;
    private Double reportPrice;
    private Appointment appointment;

}
