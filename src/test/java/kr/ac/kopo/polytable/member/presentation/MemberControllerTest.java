package kr.ac.kopo.polytable.member.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.ac.kopo.polytable.auth.application.AuthService;
import kr.ac.kopo.polytable.member.application.MemberService;
import kr.ac.kopo.polytable.member.dto.CreateRequest;
import kr.ac.kopo.polytable.member.dto.ModifiedRequest;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.member.util.GetMemberInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthService authService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void initMember() {
        CreateRequest createRequest = GetMemberInfo.initMember();
        memberService.create(createRequest.toEntity());
    }

    @Test
    @DisplayName(value = "계정 생성")
    void createMemberAndStoreTest() throws Exception {
        ObjectMapper om = new ObjectMapper();

        CreateRequest member = GetMemberInfo.bingingMember();

        String body = om.registerModule(new JavaTimeModule()).writeValueAsString(member);

        mockMvc.perform(post("/api/public/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName(value = "계정 정보 수정")
    void modifiedMemberAndStoreTest() throws Exception {

        authService.login("test2", "test2");

        ObjectMapper om = new ObjectMapper();

        ModifiedRequest request = GetMemberInfo.modifiedMemberInfo();

        String body = om.registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(put("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName(value = "계정 비활성화/삭제")
    void turnOffAccountTest() throws Exception {

        authService.login("test2", "test2");

        mockMvc.perform(delete("/api/members"))
                .andExpect(status().isNoContent())
                .andDo(print());

        Member findMember = memberRepository.findByUsername("test2").orElseThrow(
                () -> new MemberNotFoundException("존재하지 않는 멤버")
        );

        assertThat(findMember.isActivated()).isFalse();
    }

    @Test
    @DisplayName(value = "로그인 정보 조회")
    void getMyInfoTest() throws Exception {

        authService.login("test2", "test2");

        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}