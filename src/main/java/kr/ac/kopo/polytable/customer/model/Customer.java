package kr.ac.kopo.polytable.customer.model;

import kr.ac.kopo.polytable.reservation.model.Reservation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String phone;

    private String customerName;

    @OneToOne(mappedBy = "customer")
    private Reservation reservation;

    private Integer numberOfVisits;
    private Integer numberOfCancel;
    private Integer numberOfNoShow;

    private LocalDateTime recentlyVisitDate;

    @Builder
    public Customer(String phone, Integer numberOfVisits, Integer numberOfCancel, Integer numberOfNoShow, LocalDateTime recentlyVisitDate) {
        this.phone = phone;
        this.numberOfVisits = numberOfVisits;
        this.numberOfCancel = numberOfCancel;
        this.numberOfNoShow = numberOfNoShow;
        this.recentlyVisitDate = recentlyVisitDate;
    }

    // 비즈니스 로직
    public void increaseVisitCount () {
        this.numberOfVisits += 1;
    }
    public void decreaseVisitCount () {
        this.numberOfVisits -= 1;
    }
    public void decreaseCancelCount () {
        this.numberOfCancel += 1;
    }
    public void decreaseNoShowCount () {
        this.numberOfCancel += 1;
    }

    public void addNewReserve(Reservation reservation) {
        this.reservation = reservation;
    }
}
