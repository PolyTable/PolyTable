package kr.ac.kopo.polytable.store.dto;

import kr.ac.kopo.polytable.store.model.Address;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class StoreResponse implements Serializable {

    private String crn;
    private Address address;
    private String storeName;
    private String storeTelNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foundedDate;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;
}
