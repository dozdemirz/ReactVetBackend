package dev.patika.spring.Repositories;

import dev.patika.spring.Entities.Customer;
import dev.patika.spring.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    Doctor findByDoctorName(String name);


    //Doktorun müsait günlerini kontrol ettirmek için. Appointment ile bağlantısından LocalDateTime hata verdiği için custom query yazdım
    @Query("SELECT COUNT(a) > 0 FROM AvailableDate a " +
            "WHERE a.doctor.doctorId = :doctorId " +
            "AND a.availableDate = :availableDate")
    boolean isDoctorAvailableOnDate(@Param("doctorId") Long doctorId, @Param("availableDate") LocalDate availableDate);


    boolean existsByDoctorPhone(String doctorPhone);

}
