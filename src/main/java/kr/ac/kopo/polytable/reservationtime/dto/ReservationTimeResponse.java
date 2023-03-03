package kr.ac.kopo.polytable.reservationtime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTimeResponse {

    private Long reservationTimeId;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maximumHeads;

}
