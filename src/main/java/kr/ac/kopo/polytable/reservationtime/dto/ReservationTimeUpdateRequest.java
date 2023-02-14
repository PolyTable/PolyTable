package kr.ac.kopo.polytable.reservationtime.dto;

import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ReservationTimeUpdateRequest {
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maximumHeads;

    public ReservationTime toEntity() {
        return ReservationTime.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .maximumHeads(this.maximumHeads)
                .build();
    }
}
