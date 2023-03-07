package kr.ac.kopo.polytable.member.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Column(nullable = false)
    private String storeName;

    @Column(unique = true ,nullable = false)
    private String crn;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foundedDate;

    @Column(unique = true, nullable = false)
    private String storeTelNo;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime openTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime closeTime;


    @Builder
    public Store(String storeName, String crn, Address address, String storeTelNo, LocalDate foundedDate, LocalTime openTime, LocalTime closeTime) {
        this.storeName = storeName;
        this.crn = crn;
        this.address = address;
        this.storeTelNo = storeTelNo;
        this.foundedDate = foundedDate;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

}
