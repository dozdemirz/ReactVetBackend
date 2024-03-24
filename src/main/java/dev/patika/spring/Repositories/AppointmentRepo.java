package dev.patika.spring.Repositories;

import dev.patika.spring.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {


    // Randevuları tarih aralığına ve hayvana göre filtreleme
    List<Appointment> findByAppointmentDateBetweenAndAnimalAnimalId(
            LocalDateTime startDate, LocalDateTime endDate, Long animalId);

    List<Appointment> findByAppointmentDateBetweenAndDoctor_DoctorId(LocalDateTime startDate, LocalDateTime endDate, Long doctorId);

    boolean existsByAppointmentDateAndDoctor_DoctorId(LocalDateTime appointmentDate, long id);


    List<Appointment> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> findByAppointmentDateAfter(LocalDateTime startDate);
    List<Appointment> findByAppointmentDateBefore(LocalDateTime endDate);

    List<Appointment> findByDoctor_DoctorNameLikeIgnoreCase(String name);

}