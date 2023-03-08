package kr.ac.kopo.polytable.reservationtime.dto;

import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
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

    @Builder
    public ReservationTimeSaveRequest(LocalTime startTime, LocalTime endTime, Integer maximumHeads) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.maximumHeads = maximumHeads;
    }
}
