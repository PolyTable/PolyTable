package kr.ac.kopo.polytable.reservationtime.application;

import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.reservationtime.dto.ReservationTimeResponse;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTime;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTimeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
@SpringBootTest
class ReservationTimeServiceTest {

    @Autowired
    ReservationTimeService reservationTimeService;
    @MockBean
    ReservationTimeRepository reservationTimeRepository;
    @MockBean
    MemberRepository memberRepository;

    @Test
    void create_예약시간_생성() {
        Member value = Member.builder().email("email").username("username").build();
        Optional<Member> member = Optional.of(value);
        given(memberRepository.findById(1L)).willReturn(member);

        ReservationTime reservationTime = ReservationTime.builder()
                .id(1L)
                .startTime(LocalTime.of(13, 0, 0))
                .endTime(LocalTime.of(14, 0, 0))
                .maximumHeads(30)
                .member(member.get())
                .build();

        given(reservationTimeRepository.save(reservationTime)).willReturn(reservationTime);

        Long result = reservationTimeService.create(reservationTime, 1L);
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void findAll_예약시간_모두조회() {
        Member member = null;

        ReservationTime rt1 = ReservationTime.builder()
                .id(1L)
                .startTime(LocalTime.of(13, 0, 0))
                .endTime(LocalTime.of(14, 0, 0))
                .maximumHeads(30)
                .member(member)
                .build();

        ReservationTime rt2 = ReservationTime.builder()
                .id(2L)
                .startTime(LocalTime.of(15, 0, 0))
                .endTime(LocalTime.of(16, 0, 0))
                .maximumHeads(50)
                .member(member)
                .build();

        List<ReservationTime> reservationTimeList = List.of(rt1, rt2);

        member = Member.builder()
                .id(1L)
                .reservationTimes(reservationTimeList)
                .build();

        Optional<Member> optionalMember = Optional.of(member);
        given(memberRepository.findById(1L)).willReturn(optionalMember);

        List<ReservationTimeResponse> result = reservationTimeService.findAll(member.getId());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).extracting("reservationTimeId")
                .containsOnly(rt1.getId(), rt2.getId());
        assertThat(result).extracting("startTime")
                .containsOnly(rt1.getStartTime(), rt2.getStartTime());
        assertThat(result).extracting("endTime")
                .containsOnly(rt1.getEndTime(), rt2.getEndTime());
        assertThat(result).extracting("maximumHeads")
                .containsOnly(rt1.getMaximumHeads(), rt2.getMaximumHeads());
    }
}