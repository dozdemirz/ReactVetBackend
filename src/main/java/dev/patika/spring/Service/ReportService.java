package dev.patika.spring.Service;


import dev.patika.spring.Repositories.AppointmentRepo;
import dev.patika.spring.Repositories.ReportRepo;
import dev.patika.spring.Repositories.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final VaccineRepo vaccineRepo;
    private final ReportRepo reportRepo;
    private final AppointmentRepo appointmentRepo;


    @Autowired
    public ReportService(ReportRepo reportRepo, VaccineRepo vaccineRepo, AppointmentRepo appointmentRepo) {

        this.vaccineRepo = vaccineRepo;
        this.reportRepo = reportRepo;
        this.appointmentRepo = appointmentRepo;
    }



    public boolean isAppointmentExist(Long appointmentId) {
        return appointmentRepo.existsById(appointmentId);
    }



}
