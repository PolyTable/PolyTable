package kr.ac.kopo.polytable.reservationtime.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
public class ReservationTimeSaveRequest {

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @Min(1)
    @NotNull
    private Integer maximumHeads;


}
