package kr.ac.kopo.polytable.store.model;

import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String CRN;

    @OneToOne(mappedBy = "store")
    private Member owner;

    @OneToMany(mappedBy = "store")
    private List<ReservationTime> reserveTime = new ArrayList<>();

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(unique = true, nullable = false)
    private String storeTelNo;

    private LocalDateTime openTime;
    private LocalDateTime closeTime;


    @Builder
    public Store(String storeName, String CRN, Address address, String storeTelNo, Member owner,LocalDateTime openTime, LocalDateTime closeTime) {
        this.storeName = storeName;
        this.CRN = CRN;
        this.address = address;
        this.storeTelNo = storeTelNo;
        this.bindingWithOwner(owner);
        this.openTime = LocalDateTime.parse(openTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        this.closeTime = LocalDateTime.parse(closeTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
    }

    /**
     * 비즈니스 로직
     */

    public void bindingReserveTimeWithStore(ReservationTime reserveTime){
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
