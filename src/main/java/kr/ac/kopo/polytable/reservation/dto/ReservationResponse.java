package kr.ac.kopo.polytable.reservation.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ReservationResponse {
    private Long reservationId;
    private Integer heads;
    private LocalDate regDate;

    private String phone;
    private LocalTime startTime;
    private LocalTime endTime;

    @QueryProjection
    public ReservationResponse(Long reservationId, Integer heads, LocalDate regDate, String phone, LocalTime startTime, LocalTime endTime) {
        this.reservationId = reservationId;
        this.heads = heads;
        this.regDate = regDate;
        this.phone = phone;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
