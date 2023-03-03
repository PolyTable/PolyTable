package kr.ac.kopo.polytable.reservation.dto;

import kr.ac.kopo.polytable.reservation.model.Reservation;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReservationUpdateRequest {
    @NotNull
    private Integer heads;

    public Reservation toEntity() {
        return Reservation.builder()
                .heads(heads)
                .build();
    }
}
