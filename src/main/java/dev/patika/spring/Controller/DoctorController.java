package dev.patika.spring.Controller;
import dev.patika.spring.Dto.request.DoctorRequest;
import dev.patika.spring.Entities.AvailableDate;
import dev.patika.spring.Entities.Doctor;
import dev.patika.spring.Repositories.DoctorRepo;
import dev.patika.spring.Service.AvailableDateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorRepo doctorRepo;
    private final AvailableDateService availableDateService;

    public DoctorController(DoctorRepo doctorRepo, AvailableDateService availableDateService) {
        this.doctorRepo = doctorRepo;
        this.availableDateService = availableDateService;
    }

    @GetMapping("/{id}")
    public Doctor findbyId(@PathVariable("id") long id){
        return this.doctorRepo.findById(id).orElseThrow();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Doctor doctor) {
        if (doctor.getDoctorCity() == null || doctor.getDoctorCity().isEmpty() ||
                doctor.getDoctorPhone() == null || doctor.getDoctorPhone().isEmpty() ||
                doctor.getDoctorMail() == null || doctor.getDoctorMail().isEmpty() ||
                doctor.getDoctorAddress() == null || doctor.getDoctorAddress().isEmpty() ||
                doctor.getDoctorName() == null || doctor.getDoctorName().isEmpty()) {
            throw new RuntimeException("Tüm alanları doldurunuz!");
        }

        // Aynı ada sahip müşteri olabileceğinden müşterilerin benzersizliğini telefon numaralarına göre kontrol ediyorum
        String phoneNumber = doctor.getDoctorPhone();
        if (doctorRepo.existsByDoctorPhone(phoneNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Bu doktor zaten mevcut.");
        }

        Doctor savedDoctor = doctorRepo.save(doctor);
        return ResponseEntity.ok(savedDoctor);
    }

    @GetMapping("/find-all")
    public List<Doctor> findAll(){
        return this.doctorRepo.findAll();
    }

    @GetMapping("/name/{name}")
    public Doctor findByDoctorName(@PathVariable("name") String name){
        return this.doctorRepo.findByDoctorName(name);

    }
    //Doktora ait tüm çalıştığı günleri listelemek
    @GetMapping("/{doctorId}/available-dates")
    public List<AvailableDate> getAvailableDates(@PathVariable Long doctorId) {
        return availableDateService.getAvailableDates(doctorId);
    }

    //id'ye göre doktor silmek için
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable("id") long id) {
        try {
            Optional<Doctor> optionalDoctor = doctorRepo.findById(id);

            if (optionalDoctor.isPresent()) {
                doctorRepo.deleteById(id);
                return ResponseEntity.ok(id + " numaralı doktor silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir doktor bulunamadı."); // Eğer doktor bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip doktor silinemedi: " + id + ": " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable("id") long id, @RequestBody DoctorRequest doctorRequest) {
        try {
            if (doctorRequest.getDoctorCity() == null || doctorRequest.getDoctorCity().isEmpty() ||
            doctorRequest.getDoctorPhone() == null || doctorRequest.getDoctorPhone().isEmpty() ||
            doctorRequest.getDoctorMail() == null || doctorRequest.getDoctorMail().isEmpty() ||
            doctorRequest.getDoctorAddress() == null || doctorRequest.getDoctorAddress().isEmpty() ||
            doctorRequest.getDoctorName() == null || doctorRequest.getDoctorName().isEmpty()) {
                throw new RuntimeException("Tüm alanları doldurunuz!");
            }
            Optional<Doctor> optionalDoctor = doctorRepo.findById(id);

            if (optionalDoctor.isPresent()) {
                Doctor existingDoctor = optionalDoctor.get();



                if (!existingDoctor.getDoctorPhone().equals(doctorRequest.getDoctorPhone())){
                    if (!doctorRepo.existsByDoctorPhone(doctorRequest.getDoctorPhone())) {
                        existingDoctor.setDoctorPhone(doctorRequest.getDoctorPhone());

                    }else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Bu doktor zaten mevcut.");
                    }

                }
                existingDoctor.setDoctorName(doctorRequest.getDoctorName());
                existingDoctor.setDoctorMail(doctorRequest.getDoctorMail());
                existingDoctor.setDoctorAddress(doctorRequest.getDoctorAddress());
                existingDoctor.setDoctorCity(doctorRequest.getDoctorCity());

                Doctor savedDoctor = doctorRepo.save(existingDoctor);

                return ResponseEntity.ok(savedDoctor);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir müşteri bulunamadı.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip müşteri güncellenemedi: " + id + ": " + e.getMessage());
        }
    }
}
