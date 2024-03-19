package dev.patika.spring.Service;

import dev.patika.spring.Entities.AvailableDate;
import dev.patika.spring.Entities.Doctor;
import dev.patika.spring.Repositories.AvailableDateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableDateService {

    private final AvailableDateRepo availableDateRepo;

    @Autowired
    public AvailableDateService(AvailableDateRepo availableDateRepository) {
        this.availableDateRepo = availableDateRepository;
    }

    //Doktor'a ait günleri bulma
    public List<AvailableDate> getAvailableDates(Long doctorId) {
        return availableDateRepo.findByDoctor_DoctorId(doctorId);
    }


}
