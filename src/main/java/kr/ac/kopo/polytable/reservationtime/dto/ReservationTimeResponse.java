package kr.ac.kopo.polytable.reservationtime.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class ReservationTimeResponse {

    private Long reservationTimeId;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maximumHeads;

    @Builder
    public ReservationTimeResponse(Long reservationTimeId, LocalTime startTime, LocalTime endTime, Integer maximumHeads) {
        this.reservationTimeId = reservationTimeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maximumHeads = maximumHeads;
    }
}
