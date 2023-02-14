package kr.ac.kopo.polytable.store.dto;

import kr.ac.kopo.polytable.store.model.Address;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ModifiedStoreRequest {

    private String crn;
    private Address address;

    private String storeName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foundedDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    private String storeTelNo;
}
