package dev.patika.spring.Controller;


import dev.patika.spring.Dto.request.VaccineRequest;
import dev.patika.spring.Dto.response.VaccineResponse;
import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Doctor;
import dev.patika.spring.Entities.Vaccine;
import dev.patika.spring.Repositories.AnimalRepo;
import dev.patika.spring.Repositories.ReportRepo;
import dev.patika.spring.Repositories.VaccineRepo;
import dev.patika.spring.Service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vaccine")
public class VaccineController {

    private final VaccineService vaccineService;
    private final VaccineRepo vaccineRepo;
    private final AnimalRepo animalRepo;
    private final ReportRepo reportRepo;


    @GetMapping("/find-all")
    public List<Vaccine> findAll(){
        return this.vaccineRepo.findAll();
    }

    @Autowired
    public VaccineController(VaccineService vaccineService, VaccineRepo vaccineRepo, AnimalRepo animalRepo, ReportRepo reportRepo) {
        this.vaccineService = vaccineService;
        this.vaccineRepo = vaccineRepo;
        this.animalRepo = animalRepo;
        this.reportRepo = reportRepo;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createVaccine(@RequestBody VaccineRequest vaccineRequest) {
        try {
            if (vaccineRequest.getReport() == null || vaccineRequest.getVaccineName() == null ||
                    vaccineRequest.getAnimal() == null || vaccineRequest.getVaccineName().isEmpty() ||
                    vaccineRequest.getVaccineCode() == null || vaccineRequest.getVaccineCode().isEmpty() ||
                    vaccineRequest.getProtectionFinishDate() == null || vaccineRequest.getProtectionStartDate()==null ||
                    vaccineRequest.getAnimal().getAnimalId() == null || vaccineRequest.getReport().getReportId() ==null
            ) {
                throw new IllegalArgumentException("Aşıya ait alanlar boş olamaz.");
            }
            if (!vaccineService.isAnimalExist(vaccineRequest.getAnimal().getAnimalId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Belirtilen id'de hayvan mevcut değil");
            }
            if (!vaccineService.isReportExist(vaccineRequest.getReport().getReportId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Belirtilen id'de rapor mevcut değil");
            }

            VaccineResponse response = vaccineService.saveVaccine(vaccineRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<List<Vaccine>> getVaccinesByAnimalId(@PathVariable Long animalId) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByAnimalId(animalId);
        return ResponseEntity.ok(vaccines);
    }

    @GetMapping("/expiring")
    public ResponseEntity<List<Animal>> getAnimalsWithExpiringVaccines(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {
        List<Animal> animals = vaccineService.getAnimalsWithExpiringVaccines(startDate, endDate);
        return ResponseEntity.ok(animals);
    }

    @GetMapping("/expiring/{startDate}/{endDate}")
    public List<Vaccine> getExpiringVaccines(@PathVariable("startDate") LocalDate startDate, @PathVariable("endDate") LocalDate endDate) {
        return vaccineRepo.findByProtectionFinishDateBetween(startDate, endDate);
    }


    @GetMapping("/expiring-before/{endDate}")
    public List<Vaccine> getExpiringVaccinesBeforeStart(@PathVariable LocalDate endDate) {
        return vaccineRepo.findByProtectionFinishDateBefore(endDate);
    }

    @GetMapping("/expiring-after/{endDate}")
    public List<Vaccine> getExpiringVaccinesAfterStart(@PathVariable LocalDate endDate) {
        return vaccineRepo.findByProtectionFinishDateAfter(endDate);
    }


    @GetMapping("/animal-name/{name}")
    public List<Vaccine> findByAnimalName(@PathVariable("name") String animalName){
        return this.vaccineRepo.findByAnimal_AnimalNameLikeIgnoreCase("%"+animalName+"%");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVaccine(@PathVariable("id") long id) {
        try {
            Optional<Vaccine> optionalVaccine = vaccineRepo.findById(id);

            if (optionalVaccine.isPresent()) {
                vaccineRepo.deleteById(id);
                return ResponseEntity.ok(id + " numaralı aşı silindi.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir aşı bulunamadı."); // Eğer aşı bulunamazsa 404 hatası
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip aşı silinemedi: " + id + ": " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVaccine(@PathVariable Long id, @RequestBody VaccineRequest vaccineRequest) {
        try {
            // Veritabanından belirtilen ID'ye sahip aşıyı bul
            Optional<Vaccine> optionalVaccine = vaccineRepo.findById(id);

            if (optionalVaccine.isPresent()) {
                // Aşı bulunduğunda
                Vaccine vaccine = optionalVaccine.get();

                // Aşı bilgilerini güncelle
                vaccine.setVaccineName(vaccineRequest.getVaccineName());
                vaccine.setVaccineCode(vaccineRequest.getVaccineCode());
                vaccine.setProtectionStartDate(vaccineRequest.getProtectionStartDate());
                vaccine.setProtectionFinishDate(vaccineRequest.getProtectionFinishDate());

                // Aşıya ait hayvan ID'sini güncelleme işlemine dahil edebilirsiniz
                if (!vaccineService.isAnimalExist(vaccineRequest.getAnimal().getAnimalId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Belirtilen id'de hayvan mevcut değil");
                }
                vaccine.setAnimal(animalRepo.findById(vaccineRequest.getAnimal().getAnimalId()).orElseThrow(() -> new RuntimeException("Hayvan bulunamadı")));

                if (!vaccineService.isReportExist(vaccineRequest.getReport().getReportId())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Belirtilen id'de rapor mevcut değil");
                }
                vaccine.setReport(reportRepo.findById(vaccineRequest.getReport().getReportId()).orElseThrow(() -> new RuntimeException("Rapor bulunamadı")));
                // Aşıyı güncelle ve güncellenmiş aşıyı döndür
                VaccineResponse updatedVaccine = vaccineService.updateVaccine(id, vaccineRequest);
                return ResponseEntity.ok(updatedVaccine);
            } else {
                // Belirtilen ID'ye sahip aşı bulunamadığında
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bu ID'de bir aşı bulunamadı.");
            }
        } catch (Exception e) {
            // Herhangi bir hata oluştuğunda
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ID'ye sahip aşı güncellenemedi: " + id + ": " + e.getMessage());
        }
    }


    @GetMapping("/expiring/{animalName}/{startDate}/{endDate}")
    public List<Vaccine> getExpiringVaccinesWithDoctor(@PathVariable ("animalName") String animalName, @PathVariable("startDate") LocalDate startDate, @PathVariable("endDate") LocalDate endDate) {
        return vaccineRepo.findByProtectionFinishDateBetweenAndAnimal_AnimalNameLikeIgnoreCase(startDate, endDate,"%"+animalName+"%");
    }

    @GetMapping("/expiring-before/{animalName}/{endDate}")
    public List<Vaccine> getExpiringVaccinesBeforeStartWithDoctor(@PathVariable("animalName") String name,@PathVariable ("endDate") LocalDate endDate) {
        return vaccineRepo.findByProtectionFinishDateBeforeAndAnimal_AnimalNameLikeIgnoreCase(endDate,"%"+name+"%");
    }

    //http://localhost:8080/appointment/expiring-after/2023-12-28T00:00:00
    @GetMapping("/expiring-after/{animalName}/{endDate}")
    public List<Vaccine> getExpiringVaccinesAfterStartWithDoctor(@PathVariable("animalName") String name,@PathVariable ("endDate") LocalDate endDate) {
        return vaccineRepo.findByProtectionFinishDateAfterAndAnimal_AnimalNameLikeIgnoreCase(endDate,"%"+name+"%");
    }


}
