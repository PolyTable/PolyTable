package kr.ac.kopo.polytable.reservation.model;

import kr.ac.kopo.polytable.customer.model.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private Integer heads;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Builder
    public Reservation(Long id, Integer heads, Customer customer) {
        this.id = id;
        this.heads = heads;
        this.bindingWithCus(customer);
    }

    /**
     * Core
     */

    public void bindingWithCus(Customer customer) {
        this.customer = customer;
        customer.addNewReserve(this);
    }
}
