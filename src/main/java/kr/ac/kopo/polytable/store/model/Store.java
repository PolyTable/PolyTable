package kr.ac.kopo.polytable.store.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(nullable = false)
    private String storeName;

    @Column(unique = true ,nullable = false)
    private String CRN;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(unique = true, nullable = false)
    private String storeTelNo;

    private LocalDateTime openTime;
    private LocalDateTime closeTime;


    @Builder
    public Store(String storeName, String CRN, Address address, String storeTelNo, LocalDateTime openTime, LocalDateTime closeTime) {
        this.storeName = storeName;
        this.CRN = CRN;
        this.address = address;
        this.storeTelNo = storeTelNo;
        this.openTime = LocalDateTime.parse(openTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        this.closeTime = LocalDateTime.parse(closeTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
    }

    /**
     * 비즈니스 로직
     */

    public void modifiedStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void modifiedCRN(String CRN) {
        this.CRN = CRN;
    }

    public void modifiedAddress(Address address) {
        this.address = address;
    }

    public void modifiedStoreTelNo(String storeTelNo) {
        this.storeTelNo = storeTelNo;
    }

    public void modifiedOpenTime(LocalDateTime openTime) {
        this.openTime = LocalDateTime.parse(openTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
    }

    public void modifiedCloseTime(LocalDateTime closeTime) {
        this.closeTime = LocalDateTime.parse(closeTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
    }
}
