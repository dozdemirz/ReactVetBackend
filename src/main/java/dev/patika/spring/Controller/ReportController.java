package dev.patika.spring.Controller;

import dev.patika.spring.Dto.request.ReportRequest;
import dev.patika.spring.Entities.Appointment;
import dev.patika.spring.Repositories.AppointmentRepo;
import dev.patika.spring.Repositories.ReportRepo;
import dev.patika.spring.Service.AnimalService;
import dev.patika.spring.Service.ReportService;
import dev.patika.spring.Service.VaccineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.patika.spring.Entities.Report;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportRepo reportRepo;
    private final AppointmentRepo appointmentRepo;
    private final ReportService reportService;

    public ReportController(ReportRepo reportRepo, AppointmentRepo appointmentRepo, ReportService reportService) {
        this.reportRepo = reportRepo;
        this.appointmentRepo = appointmentRepo;
        this.reportService = reportService;
    }
    @GetMapping("/{id}")
    public Report findbyId(@PathVariable("id") long id){return this.reportRepo.findById(id).orElseThrow();}


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Report report) {
        String reportName = report.getReportTitle();
        if (reportRepo.existsByReportTitle(reportName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu rapor zaten mevcut!");
        }

        Appointment appointment = report.getAppointment();
        if (report.getAppointment() != null) {
            Optional<Appointment> existingAppointment = appointmentRepo.findById(report.getAppointment().getAppointmentId());
            existingAppointment.ifPresent(report::setAppointment);
        }

        Report savedReport = reportRepo.save(report);
        return ResponseEntity.ok(savedReport);
    }


    @GetMapping("/find-all")
    public List<Report> findAll(){return this.reportRepo.findAll();}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable("id") long id) {
        try {
            Optional<Report> optionalReport = reportRepo.findById(id);

            if (optionalReport.isPresent()) {
                Report report = optionalReport.get();
                reportRepo.deleteById(id);

                if (reportRepo.findById(id).isEmpty()) {
                    return ResponseEntity.ok(report.getReportTitle() + " başlıklı rapor başarıyla silindi.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Rapor silinirken bir hata oluştu.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir rapor bulunamadı."); // Eğer rapor bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip rapor silinemedi: " + id + ": " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReport(@PathVariable("id") long id, @RequestBody ReportRequest reportRequest) {
        if (reportRequest.getReportTitle() == null || reportRequest.getReportTitle().isEmpty() ||
                reportRequest.getReportDiagnosis() == null ||reportRequest.getReportDiagnosis().isEmpty() ||
                reportRequest.getReportPrice() == null ||
                reportRequest.getAppointment() == null ||reportRequest.getAppointment().getAppointmentId()==null
        ) {
            throw new IllegalArgumentException("Rapora ait alanlar boş olamaz.");
        }

        try {
            Optional<Report> optionalReport = reportRepo.findById(id);
            if (reportRequest.getAppointment() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Randevu bilgisi eksik.");
            }

            if (optionalReport.isPresent()) {
                Report report = optionalReport.get();


                report.setReportTitle(reportRequest.getReportTitle());
                report.setReportDiagnosis(reportRequest.getReportDiagnosis());
                report.setReportPrice(reportRequest.getReportPrice());

                if (!reportService.isAppointmentExist(reportRequest.getAppointment().getAppointmentId())){
                    return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("Belirtilen ID'de bir randevu bulunmuyor.");
                }
                report.setAppointment(appointmentRepo.findById(reportRequest.getAppointment().getAppointmentId()).orElseThrow(() -> new RuntimeException("Randevu bulunamadı!")));



                if (!(report.getReportTitle().equals(reportRequest.getReportTitle())) || !(report.getAppointment().getAppointmentId().equals(reportRequest.getAppointment().getAppointmentId()))){
                    if (reportRepo.existsByReportTitleAndAppointment_AppointmentId(reportRequest.getReportTitle(),reportRequest.getAppointment().getAppointmentId())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bu rapor zaten mevcut!");
                    }
                }
                Report updatedReport = reportRepo.save(report);

                return ResponseEntity.ok(updatedReport);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir rapor bulunamadı.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip rapor güncellenemedi: " + id + ": " + e.getMessage());
        }
    }


}
