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
}
