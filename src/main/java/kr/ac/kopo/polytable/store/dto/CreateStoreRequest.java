package kr.ac.kopo.polytable.store.dto;

import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.store.model.Address;
import kr.ac.kopo.polytable.store.model.Store;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CreateStoreRequest {

    @NotNull
    private String storeName;

    @NotNull
    private String crn;

    private List<Address> address;

    private String storeTelNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foundedDate;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    public Store toEntity() {
        Address paramAdd = Address.of(address.get(0)
        );


        return Store.builder()
                .storeName(storeName)
                .crn(crn)
                .address(paramAdd)
                .storeTelNo(storeTelNo)
                .foundedDate(foundedDate)
                .openTime(openTime)
                .closeTime(closeTime)
                .build();
    }
}