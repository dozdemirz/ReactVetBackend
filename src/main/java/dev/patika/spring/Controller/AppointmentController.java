package dev.patika.spring.Controller;


import dev.patika.spring.Dto.request.AppointmentRequest;
import dev.patika.spring.Entities.Appointment;
import dev.patika.spring.Repositories.AnimalRepo;
import dev.patika.spring.Repositories.AppointmentRepo;
import dev.patika.spring.Repositories.DoctorRepo;
import dev.patika.spring.Service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentRepo appointmentRepo;

    private final DoctorRepo doctorRepo;

    private final AnimalRepo animalRepo;

    public AppointmentController(AppointmentService appointmentService, AppointmentRepo appointmentRepo, DoctorRepo doctorRepo, AnimalRepo animalRepo) {
        this.appointmentService = appointmentService;
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
        this.animalRepo = animalRepo;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        try {
            Appointment response = appointmentService.createAppointment(appointmentRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable("id") long id, @RequestBody AppointmentRequest appointmentRequest) {
        try {
            Optional<Appointment> optionalAppointment = appointmentRepo.findById(id);

            if (!optionalAppointment.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir randevu bulunamadı.");
            }

            Appointment appointment = optionalAppointment.get();

            appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());

            if (appointmentRequest.getDoctor() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doktor bilgisi eksik.");
            }
            if (appointmentRequest.getAnimal() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hayvan bilgisi eksik.");
            }

            if (!appointmentService.isDoctorExist(appointmentRequest.getDoctor().getDoctorId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Belirtilen ID'de bir doktor bulunmuyor.");
            }
            if (!appointmentService.isAnimalExist(appointmentRequest.getAnimal().getAnimalId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Belirtilen ID'de bir hayvan bulunmuyor.");
            }

            appointment.setDoctor(doctorRepo.findById(appointmentRequest.getDoctor().getDoctorId()).orElseThrow(() -> new RuntimeException("Doktor bulunamadı!")));
            appointment.setAnimal(animalRepo.findById(appointmentRequest.getAnimal().getAnimalId()).orElseThrow(() -> new RuntimeException("Hayvan bulunamadı!")));

            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentRequest);

            return ResponseEntity.ok(updatedAppointment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Randevu güncellenemedi: "  + e.getMessage());
        }
    }

    //appointmentId sayesinde appointment'ı getirmek için
    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    //İki date arasındaki istenen doktora ait tüm randevular
    @GetMapping("/findByDateAndDoctor")
    public ResponseEntity<List<Appointment>> findAppointmentsByDateAndDoctor(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam Long doctorId) {
        List<Appointment> appointments = appointmentService.findAppointmentsByDateAndDoctor(startDate, endDate, doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<Appointment>> findAllAppointments() {
        List<Appointment> appointments = appointmentService.findAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    //İki date arasındaki hayvana ait tüm randevular
    @GetMapping("/findByDateAndAnimal")
    public ResponseEntity<List<Appointment>> findAppointmentsByDateAndAnimal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam Long animalId) {
        List<Appointment> appointments = appointmentService.findAppointmentsByDateAndAnimal(startDate, endDate, animalId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    //id'ye göre istenen randevuyu silmek için
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("id") long id) {
        try {
            Optional<Appointment> optionalAppointment = appointmentRepo.findById(id);

            if (optionalAppointment.isPresent()) {
                appointmentRepo.deleteById(id);
                return ResponseEntity.ok(id + " numaralı randevu silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir randevu bulunamadı."); // Eğer randevu bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip randevu silinemedi: " + id + ": " + e.getMessage());
        }
    }
}
