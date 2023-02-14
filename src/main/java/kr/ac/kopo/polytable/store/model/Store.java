package kr.ac.kopo.polytable.store.model;

import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

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
    private String crn;

    @OneToOne(mappedBy = "store")
    private Member owner;

    @OneToMany(mappedBy = "store")
    private List<ReservationTime> reserveTime = new ArrayList<>();

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

    /**
     * 비즈니스 로직
     */

    public void bindingReserveTimeWithStore(ReservationTime reserveTime) {
        this.reserveTime.add(reserveTime);
        reserveTime.addNewStore(this);
    }

    public void bindingWithOwner(Member owner) {
        this.owner = owner;
        owner.addNewStore(this);
    }


    public void modifiedOwner(Member newOwner) {
        this.owner.removeStoreFromOwner();
        this.owner = newOwner;
        newOwner.addNewStore(this);
    }

    public void removeOwner() {
        this.owner = null;
    }

    public void modifiedStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void modifiedCrn(String crn) {
        this.crn = crn;
    }

    public void modifiedAddress(Address address) {
        this.address = address;
    }

    public void modifiedStoreTelNo(String storeTelNo) {
        this.storeTelNo = storeTelNo;
    }

    public void modifiedOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public void modifiedCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public void modifiedFoundedDate(LocalDate foundedDate) {
        this.foundedDate = foundedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Store store = (Store) o;

        return id.equals(store.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
