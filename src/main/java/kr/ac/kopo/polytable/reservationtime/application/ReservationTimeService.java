package kr.ac.kopo.polytable.reservationtime.application;

import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.modelmapper.CustomModelMapper;
import kr.ac.kopo.polytable.reservationtime.dto.ReservationTimeResponse;
import kr.ac.kopo.polytable.reservationtime.error.ReservationTimeNotFoundException;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTimeRepository;
import kr.ac.kopo.polytable.store.error.StoreNotFoundException;
import kr.ac.kopo.polytable.store.model.Store;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final MemberRepository memberRepository;
    private final CustomModelMapper customModelMapper;


    public Long create(ReservationTime reservationTime, Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Store store = member.getStore();

        if (store == null) {
            throw new StoreNotFoundException("회원의 가게가 존재하지 않습니다");
        }

        reservationTime.addNewStore(store);

        return reservationTimeRepository.save(reservationTime).getId();
    }

    public List<ReservationTimeResponse> findAll(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Store store = member.getStore();

        if (store == null) {
            throw new StoreNotFoundException("회원의 가게가 존재하지 않습니다");
        }

        return store.getReserveTime()
                .stream()
                .map(rt -> new ReservationTimeResponse(
                        rt.getId(),
                        rt.getStartTime(),
                        rt.getEndTime(),
                        rt.getMaximumHeads()))
                .toList();
    }


    public ReservationTimeResponse findById(Long reservationTimeId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Store store = member.getStore();

        ReservationTime reservationTime = reservationTimeRepository
                .findById(reservationTimeId).orElseThrow(ReservationTimeNotFoundException::new);

        if (store.equals(reservationTime.getStore())) {
            //에러발생
            throw new RuntimeException("사용자의 가게에 속해있는 예약시간이 아닙니다.");
        }

        ModelMapper mapper = customModelMapper.standardMapper();
        return mapper.map(reservationTime, ReservationTimeResponse.class);
    }

    public void update(ReservationTime updateRequest, Long reservationTimeId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Store store = member.getStore();

        ReservationTime reservationTime = reservationTimeRepository
                .findById(reservationTimeId).orElseThrow(ReservationTimeNotFoundException::new);

        if (store.equals(reservationTime.getStore())) {
            //에러발생
            throw new RuntimeException("사용자의 가게에 속해있는 예약시간이 아닙니다.");
        }

        reservationTime.updateReservationTime(updateRequest);
    }

    public void delete(Long reservationTimeId, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Store store = member.getStore();

        ReservationTime reservationTime = reservationTimeRepository
                .findById(reservationTimeId).orElseThrow(ReservationTimeNotFoundException::new);

        if (store.equals(reservationTime.getStore())) {
            //에러발생
            throw new RuntimeException("사용자의 가게에 속해있는 예약시간이 아닙니다.");
        }

        reservationTimeRepository.deleteById(reservationTimeId);
    }
}
