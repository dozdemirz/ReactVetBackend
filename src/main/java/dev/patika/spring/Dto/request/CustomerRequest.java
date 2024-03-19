package dev.patika.spring.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRequest {
    private long customerId;
    private String customerName;
    private String customerPhone;
    private String customerMail;
    private String customerAddress;
    private String customerCity;
}
