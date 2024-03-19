package dev.patika.spring.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorRequest {
    private long doctorId;
    private String doctorName;
    private String doctorPhone;
    private String doctorMail;
    private String doctorAddress;
    private String doctorCity;
}
