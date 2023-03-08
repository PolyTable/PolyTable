package kr.ac.kopo.polytable.reservationtime.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.ac.kopo.polytable.auth.application.AuthService;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.member.application.MemberService;
import kr.ac.kopo.polytable.member.dto.CreateRequest;
import kr.ac.kopo.polytable.member.dto.RoleType;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.member.util.GetMemberInfo;
import kr.ac.kopo.polytable.reservationtime.application.ReservationTimeService;
import kr.ac.kopo.polytable.reservationtime.dto.ReservationTimeResponse;
import kr.ac.kopo.polytable.reservationtime.dto.ReservationTimeSaveRequest;
import kr.ac.kopo.polytable.reservationtime.model.ReservationTimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ReservationTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AuthService authService;
    @Autowired
    MemberService memberService;

    @MockBean
    private ReservationTimeService reservationTimeService;

    private final String USERNAME = "test";
    private final String PASSWORD = "test";

    @BeforeEach
    void setUp() {
        CreateRequest request = GetMemberInfo.bingingMember();
        memberService.create(request.toEntity());
        authService.login(USERNAME, PASSWORD);
    }

    @Test
    void 예약시간_생성() throws Exception {
        ObjectMapper om = new ObjectMapper();

        final LocalTime starTime = LocalTime.of(13, 0, 0);
        final LocalTime endTime = LocalTime.of(13, 0, 0);
        final Integer maximumHeads = 30;

        ReservationTimeSaveRequest reservationTime = ReservationTimeSaveRequest.builder()
                .startTime(starTime)
                .endTime(endTime)
                .maximumHeads(maximumHeads)
                .build();

        String body = om.registerModule(new JavaTimeModule()).writeValueAsString(reservationTime);

        mockMvc.perform(post("/api/reservationTime")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void 예약시간_모두조회() throws Exception {
        ReservationTimeResponse data1 = ReservationTimeResponse.builder()
                .reservationTimeId(1L)
                .startTime(LocalTime.of(14, 0, 0))
                .endTime(LocalTime.of(14, 30, 0))
                .maximumHeads(40)
                .build();

        ReservationTimeResponse data2 = ReservationTimeResponse.builder()
                .reservationTimeId(2L)
                .startTime(LocalTime.of(15, 0, 0))
                .endTime(LocalTime.of(15, 30, 0))
                .maximumHeads(50)
                .build();


        List<ReservationTimeResponse> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);


        given(reservationTimeService.findAll(1L)).willReturn(list);

        mockMvc.perform(get("/api/reservationTime"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 예약시간_단건조회() throws Exception {
        ReservationTimeResponse data1 = ReservationTimeResponse.builder()
                .reservationTimeId(1L)
                .startTime(LocalTime.of(14, 0, 0))
                .endTime(LocalTime.of(14, 30, 0))
                .maximumHeads(40)
                .build();

        given(reservationTimeService.findById(1L, 1L)).willReturn(data1);

        mockMvc.perform(get("/api/reservationTime/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}