package kr.ac.kopo.polytable.reservation.model;

import kr.ac.kopo.polytable.customer.model.Customer;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private Integer heads;

    @Column(nullable = false)
    private LocalDate regDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_time_id")
    private ReservationTime reservationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Reservation(Integer heads, LocalDate regDate, Customer customer, ReservationTime reservationTime) {
        this.heads = heads;
        this.regDate = regDate;
        this.customer = customer;
        this.reservationTime = reservationTime;
    }




    /**
     * Core
     */

    public void bindingWithCus(Customer customer) {
        this.customer = customer;
        customer.addNewReserve(this);
    }

    public void updateReservation(Reservation request) {
        this.heads = request.getHeads();
        this.regDate = request.getRegDate();
    }
}
