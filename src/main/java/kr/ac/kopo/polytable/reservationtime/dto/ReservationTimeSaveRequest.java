package kr.ac.kopo.polytable.reservationtime.dto;

import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
public class ReservationTimeSaveRequest {

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Min(1)
    @NotNull
    private Integer maximumHeads;

    public ReservationTime toEntity() {
        return ReservationTime.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .maximumHeads(this.maximumHeads)
                .build();
    }

}
