package dev.patika.spring.Repositories;

import dev.patika.spring.Entities.Appointment;
import dev.patika.spring.Entities.AvailableDate;
import dev.patika.spring.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> {

    boolean existsByDoctorAndAvailableDate(Doctor doctor, LocalDate availableDate);
    List<AvailableDate> findByDoctor_DoctorId(Long doctorId);


    boolean existsByDoctor_DoctorIdAndAvailableDate(Long doctorId, LocalDate availableDate);

}
