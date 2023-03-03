package kr.ac.kopo.polytable.reservation.application;

import kr.ac.kopo.polytable.customer.error.CustomerNotFoundException;
import kr.ac.kopo.polytable.customer.model.Customer;
import kr.ac.kopo.polytable.customer.model.CustomerRepository;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.reservation.dto.ReservationResponse;
import kr.ac.kopo.polytable.reservation.error.ReservationNotFoundException;
import kr.ac.kopo.polytable.reservation.model.Reservation;
import kr.ac.kopo.polytable.reservation.model.repository.ReservationRepository;
import kr.ac.kopo.polytable.reservationtime.error.ReservationTimeNotFoundException;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final MemberRepository memberRepository;
    private final CustomerRepository customerRepository;


    /**
     * 예약생성
     * @param request = 예약생성 정보
     * @param reservationTimeId = 예약시간아이디
     * @param phone = 예약자 핸드폰번호
     * @param memberId = 현재 사용자 아이디
     * @return 생성된 예약 아이디
     */
    public Long create(Reservation request, Long reservationTimeId, String phone, Long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        ReservationTime reservationTime =
                reservationTimeRepository
                        .findById(reservationTimeId).orElseThrow(() -> new ReservationTimeNotFoundException());

        Integer headsOfReservationTime =
                reservationRepository.countByReservePeople(request.getRegDate(), reservationTimeId);

        //선택한 예약시간에 예약가능 여부 확인 (인원수 체크)
        reservationTime.validMaximumHeads(headsOfReservationTime + request.getHeads());

        Customer customer = customerRepository.findByPhone(phone);

        //처음 방문한 고객일 때
        if (customer == null) {
            customerRepository.save(
                    Customer.builder()
                            .phone(phone)
                            .numberOfVisits(1)
                            .build());
        } else {
            customer.increaseVisitCount();
        }

        return reservationRepository.save(request).getId();
    }

    /**
     * 예약변경
     * @param request = 예약변경 정보
     * @param reservationId = 변경할 예약 아이디
     * @param memberId = 현재 사용자 아이디
     */
    public void update(Reservation request, Long reservationId, Long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        Reservation reservation = reservationRepository
                .findById(reservationId).orElseThrow(() -> new ReservationNotFoundException());

        ReservationTime reservationTime = reservation.getReservationTime();

        Integer headsOfReservationTime =
                reservationRepository.countByReservePeople(request.getRegDate(), reservationTime.getId());

        reservationTime.validMaximumHeads(headsOfReservationTime + request.getHeads());

        reservation.updateReservation(request);
    }

    /**
     * 예약 삭제
     * @param reservationId = 삭제할 예약 아이디
     * @param memberId = 현재 사용자 아이디
     */
    public void delete(Long reservationId, Long memberId) {
        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        reservationRepository.findById(reservationId).orElseThrow(() -> new ReservationNotFoundException());

        reservationRepository.deleteById(reservationId);

    }

    public List<ReservationResponse> findAllByReservationResponse(Long memberId) {
        return reservationRepository.findAllByReservationResponse(memberId);
    }
}
