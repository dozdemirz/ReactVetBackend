package dev.patika.spring.Service;

import dev.patika.spring.Dto.request.VaccineRequest;
import dev.patika.spring.Dto.response.VaccineResponse;
import dev.patika.spring.Entities.Animal;
import dev.patika.spring.Entities.Report;
import dev.patika.spring.Entities.Vaccine;
import dev.patika.spring.Repositories.AnimalRepo;
import dev.patika.spring.Repositories.ReportRepo;
import dev.patika.spring.Repositories.VaccineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VaccineService {
    private final VaccineRepo vaccineRepository;
    private final AnimalRepo animalRepo;
    private  final ReportRepo reportRepo;

    @Autowired
    public VaccineService(VaccineRepo vaccineRepository, AnimalRepo animalRepo, ReportRepo reportRepo) {
        this.vaccineRepository = vaccineRepository;
        this.animalRepo = animalRepo;
        this.reportRepo = reportRepo;
    }

    //Aşı ekleme
    public VaccineResponse saveVaccine(VaccineRequest vaccineRequest) {
        Vaccine vaccine = new Vaccine();
        vaccine.setVaccineName(vaccineRequest.getVaccineName());
        vaccine.setVaccineCode(vaccineRequest.getVaccineCode());
        vaccine.setProtectionStartDate(vaccineRequest.getProtectionStartDate());
        vaccine.setProtectionFinishDate(vaccineRequest.getProtectionFinishDate());


        Animal animal = animalRepo.findById(vaccineRequest.getAnimal().getAnimalId())
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip hayvan bulunamadı."));
        vaccine.setAnimal(animal);
        Report report = reportRepo.findById(vaccineRequest.getReport().getReportId())
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip rapor bulunamadı."));
        vaccine.setReport(report);

        List<Vaccine> vaccines = vaccineRepository.findByAnimalIdAndVaccineNameAndVaccineCode(vaccineRequest.getAnimal().getAnimalId(),vaccineRequest.getVaccineName(),vaccineRequest.getVaccineCode());

        if (!vaccines.isEmpty()) {
            throw new RuntimeException("Aynı tarihlerde aynı hayvana aynı aşıyı tekrar ekleyemezsiniz.");
        }
        //DEĞERLENDİRME FORMU 19
        if (vaccine.getProtectionFinishDate().isBefore(vaccine.getProtectionStartDate())) {
            throw new RuntimeException("Koruma bitiş tarihi koruma başlangıç tarihinden önce olamaz.");
        }

        vaccineRepository.save(vaccine);

        VaccineResponse vaccineResponse = new VaccineResponse();
        vaccineResponse.setVaccineId(vaccine.getVaccineId());
        vaccineResponse.setVaccineName(vaccine.getVaccineName());
        vaccineResponse.setVaccineCode(vaccine.getVaccineCode());
        vaccineResponse.setProtectionStartDate(vaccine.getProtectionStartDate());
        vaccineResponse.setProtectionFinishDate(vaccine.getProtectionFinishDate());

        // Animal nesnesini kontrol et ve eğer null değilse atamaları yap
        if (vaccine.getAnimal() != null) {
            long animalId = vaccine.getAnimal().getAnimalId();
            Optional<Animal> animalVaccine = animalRepo.findById(animalId);
            vaccineResponse.setAnimal(animalVaccine.orElse(null));
        }
        if (vaccine.getReport() != null) {
            long reportId = vaccine.getReport().getReportId();
            Optional<Report> reportVaccine = reportRepo.findById(reportId);
            vaccineResponse.setReport(reportVaccine.orElse(null));
        }

        return vaccineResponse;
    }

    public VaccineResponse updateVaccine(Long id, VaccineRequest vaccineRequest) {
        // Belirtilen ID'ye sahip aşıyı bul
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip aşı bulunamadı."));

        if (vaccineRequest.getReport() == null || vaccineRequest.getVaccineName() == null ||
                vaccineRequest.getAnimal() == null || vaccineRequest.getVaccineName().isEmpty() ||
                vaccineRequest.getVaccineCode() == null || vaccineRequest.getVaccineCode().isEmpty() ||
                vaccineRequest.getProtectionFinishDate() == null || vaccineRequest.getProtectionStartDate()==null ||
                vaccineRequest.getAnimal().getAnimalId() == null || vaccineRequest.getReport().getReportId() ==null
        ) {
            throw new IllegalArgumentException("Aşıya ait alanlar boş olamaz.");
        }
        // Aşıyı güncelle
        vaccine.setVaccineName(vaccineRequest.getVaccineName());
        vaccine.setVaccineCode(vaccineRequest.getVaccineCode());
        vaccine.setProtectionStartDate(vaccineRequest.getProtectionStartDate());
        vaccine.setProtectionFinishDate(vaccineRequest.getProtectionFinishDate());

        // Aşıya ait hayvanı kontrol et ve eğer null değilse atamaları yap
        if (vaccineRequest.getAnimal() != null) {
            Animal animal = animalRepo.findById(vaccineRequest.getAnimal().getAnimalId())
                    .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip hayvan bulunamadı."));
            vaccine.setAnimal(animal);
        }
        if (vaccineRequest.getReport() != null) {
            Report report = reportRepo.findById(vaccineRequest.getReport().getReportId())
                    .orElseThrow(() -> new RuntimeException("Belirtilen ID'ye sahip rapor bulunamadı."));
            vaccine.setReport(report);
        }


        // Belirtilen aşıya sahip olmayan diğer aşıları kontrol et
        List<Vaccine> vaccines = vaccineRepository.findByAnimal_AnimalIdAndVaccineNameAndVaccineCodeAndVaccineIdNot(vaccineRequest.getAnimal().getAnimalId(), vaccineRequest.getVaccineName(), vaccineRequest.getVaccineCode(), id);

        if (!vaccines.isEmpty()) {
            throw new RuntimeException("Aynı tarihlerde aynı hayvana aynı aşıyı tekrar ekleyemezsiniz.");
        }

        if (vaccineRequest.getProtectionFinishDate().isBefore(vaccineRequest.getProtectionStartDate())) {
            throw new RuntimeException("Koruma bitiş tarihi koruma başlangıç tarihinden önce olamaz.");
        }

        // Aşıyı güncelle

        vaccineRepository.save(vaccine);

        // Aşıya ait bilgileri oluştur ve döndür
        VaccineResponse vaccineResponse = new VaccineResponse();
        vaccineResponse.setVaccineId(vaccine.getVaccineId());
        vaccineResponse.setVaccineName(vaccine.getVaccineName());
        vaccineResponse.setVaccineCode(vaccine.getVaccineCode());
        vaccineResponse.setProtectionStartDate(vaccine.getProtectionStartDate());
        vaccineResponse.setProtectionFinishDate(vaccine.getProtectionFinishDate());

        // Aşıya ait hayvan bilgisini kontrol et ve eğer null değilse atamaları yap
        if (vaccine.getAnimal() != null) {
            long animalId = vaccine.getAnimal().getAnimalId();
            Optional<Animal> animalVaccine = animalRepo.findById(animalId);
            vaccineResponse.setAnimal(animalVaccine.orElse(null));
        }
        if (vaccine.getReport() != null) {
            long reportId = vaccine.getReport().getReportId();
            Optional<Report> reportVaccine = reportRepo.findById(reportId);
            vaccineResponse.setReport(reportVaccine.orElse(null));
        }

        return vaccineResponse;
    }


    public List<Vaccine> getVaccinesByAnimalId(Long animalId) {
        return vaccineRepository.findByAnimal_AnimalId(animalId);
    }

    public List<Animal> getAnimalsWithExpiringVaccines(LocalDate startDate, LocalDate endDate) {
        return vaccineRepository.findAnimalsWithExpiringVaccines(startDate, endDate);
    }

    public boolean isAnimalExist(Long animalId) {
        return animalRepo.existsById(animalId);
    }

    public boolean isReportExist(Long reportId) {
        return reportRepo.existsById(reportId);
    }



}
