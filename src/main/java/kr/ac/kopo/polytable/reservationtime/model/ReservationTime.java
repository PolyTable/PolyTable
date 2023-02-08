package kr.ac.kopo.polytable.reservationtime.model;

import kr.ac.kopo.polytable.store.model.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_time_id")
    private Long id;


    @OneToMany(mappedBy = "store_id")
    private List<Store> stores = new ArrayList<>();

    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;


    @Column(nullable = false)
    private Integer maximumHeads;

    @Builder
    public ReservationTime(LocalTime startTime, LocalTime endTime, Integer maximumHeads) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.maximumHeads = maximumHeads;
    }

    /**
     * core
     */

    public void addNewStore(Store store) {
        this.stores.add(store);
    }
}
