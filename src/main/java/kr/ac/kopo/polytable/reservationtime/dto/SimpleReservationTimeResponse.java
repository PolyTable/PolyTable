package kr.ac.kopo.polytable.reservationtime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleReservationTimeResponse {
    private Long reservationTimeId;
    private boolean result;
}
