package kr.ac.kopo.polytable.reservationtime.application;

import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.modelmapper.CustomModelMapper;
import kr.ac.kopo.polytable.reservationtime.dto.ReservationTimeResponse;
import kr.ac.kopo.polytable.reservationtime.error.ReservationTimeNotFoundException;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTimeRepository;
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

        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        reservationTime.addNewMember(member);

        return reservationTimeRepository.save(reservationTime).getId();
    }

    public List<ReservationTimeResponse> findAll(Long memberId) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        if (member == null) {
            throw new MemberNotFoundException("존재하지 않는 사용자입니다.");
        }

        return member.getReservationTimes()
                .stream()
                .map(rt -> new ReservationTimeResponse(
                        rt.getId(),
                        rt.getStartTime(),
                        rt.getEndTime(),
                        rt.getMaximumHeads()))
                .toList();
    }


    public ReservationTimeResponse findById(Long reservationTimeId, Long memberId) {
        Member member = memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        ReservationTime reservationTime = reservationTimeRepository
                .findById(reservationTimeId)
                .orElseThrow(() -> new ReservationTimeNotFoundException("존재하지 않는 예약시간입니다."));

        if (member.equals(reservationTime.getMember())) {
            //에러발생
            throw new RuntimeException("사용자의 가게에 속해있는 예약시간이 아닙니다.");
        }

        ModelMapper mapper = customModelMapper.standardMapper();
        return mapper.map(reservationTime, ReservationTimeResponse.class);
    }

    public void update(ReservationTime updateRequest, Long reservationTimeId, Long memberId) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        ReservationTime reservationTime = reservationTimeRepository
                .findById(reservationTimeId)
                .orElseThrow(() -> new ReservationTimeNotFoundException("존재하지 않는 예약시간입니다."));

        if (!member.equals(reservationTime.getMember())) {
            //에러발생
            throw new RuntimeException("사용자의 가게에 속해있는 예약시간이 아닙니다.");
        }

        reservationTime.updateReservationTime(updateRequest);
    }

    public void delete(Long reservationTimeId, Long memberId) {
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자입니다."));

        ReservationTime reservationTime = reservationTimeRepository
                .findById(reservationTimeId)
                .orElseThrow(() -> new ReservationTimeNotFoundException("존재하지 않는 예약시간입니다."));

        if (member.equals(reservationTime.getMember())) {
            //에러발생
            throw new RuntimeException("사용자의 가게에 속해있는 예약시간이 아닙니다.");
        }

        reservationTimeRepository.deleteById(reservationTimeId);
    }
}
