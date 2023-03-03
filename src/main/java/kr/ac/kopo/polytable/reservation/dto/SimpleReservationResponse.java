package kr.ac.kopo.polytable.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleReservationResponse {
    private Long reservationTimeId;
    private boolean result;
}
