package kr.ac.kopo.polytable.reservationtime.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime regTime;

    @Column(nullable = false)
    private Integer maximumHeads;

    @Builder
    public ReservationTime(LocalTime regTime, Integer maximumHeads) {
        this.regTime = regTime;
        this.maximumHeads = maximumHeads;
    }


}
