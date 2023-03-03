package kr.ac.kopo.polytable.customer.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CustomerResponse {
    String phone;
    String customerName;

    private Integer numberOfVisits;
    private Integer numberOfCancel;
    private Integer numberOfNoShow;

    private LocalDateTime recentlyVisitDate;
}
