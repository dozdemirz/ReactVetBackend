package dev.patika.spring.Repositories;

import dev.patika.spring.Entities.Report;
import dev.patika.spring.Entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {
     Report findByReportTitle(String title);

     boolean existsByReportTitle (String reportTitle);


     boolean existsByReportTitleAndAppointment_AppointmentId(String title ,Long id);

}
