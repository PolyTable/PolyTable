package kr.ac.kopo.polytable.reservation.dto;

import kr.ac.kopo.polytable.reservation.model.Reservation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ReservationSaveRequest {
    @NotNull
    private Integer heads;
    @NotNull
    private LocalDate regDate;
    @NotBlank
    private String phone;

    public Reservation toEntity() {
        return Reservation.builder()
                .heads(heads)
                .regDate(regDate)
                .build();
    }

}
