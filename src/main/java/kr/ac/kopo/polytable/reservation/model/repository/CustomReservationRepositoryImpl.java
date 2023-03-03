package kr.ac.kopo.polytable.reservation.model.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.kopo.polytable.reservation.dto.QReservationResponse;
import kr.ac.kopo.polytable.reservation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static kr.ac.kopo.polytable.customer.model.QCustomer.customer;
import static kr.ac.kopo.polytable.reservation.model.QReservation.*;
import static kr.ac.kopo.polytable.reservationtime.model.QReservationTime.*;

@Repository
@RequiredArgsConstructor
public class CustomReservationRepositoryImpl implements CustomReservationRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Integer countByReservePeople(LocalDate regDate, Long reservationTimeId) {
         return queryFactory
                 .select(reservation.heads.sum())
                 .from(reservation.reservationTime, reservationTime)
                 .where(reservation.regDate.eq(regDate).and(reservationTime.id.eq(reservationTimeId)))
                 .fetchOne();
    }

    @Override
    public List<ReservationResponse> findAllByReservationResponse(Long memberId) {
        return queryFactory
                .select(new QReservationResponse(
                        reservation.id,
                        reservation.heads,
                        reservation.regDate,
                        reservation.customer.phone,
                        reservationTime.startTime,
                        reservationTime.endTime))
                .from(reservation)
                .leftJoin(reservation.reservationTime, reservationTime)
                .leftJoin(reservation.customer, customer)
                .where(reservation.member.id.eq(memberId))
                .fetch();
    }
}
