package kr.ac.kopo.polytable.reservation.model.repository;

import kr.ac.kopo.polytable.reservation.dto.ReservationResponse;

import java.time.LocalDate;
import java.util.List;

public interface CustomReservationRepository {

    //특정 날짜에 특정 예약시간에 예약한 사람의 수
    Integer countByReservePeople(LocalDate regDate, Long reservationTimeId);


    List<ReservationResponse> findAllByReservationResponse(Long memberId);
}
