package kr.ac.kopo.polytable.member.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetailsService;
import kr.ac.kopo.polytable.member.application.MemberService;
import kr.ac.kopo.polytable.member.dto.CreateRequest;
import kr.ac.kopo.polytable.member.dto.ModifiedRequest;
import kr.ac.kopo.polytable.member.dto.SimpleMemberResponse;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Address;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.Store;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.member.util.GetMemberInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

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
    @WithUserDetails(value = "test")
    @DisplayName(value = "계정 정보 수정")
    void modifiedMemberAndStoreTest() throws Exception {

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
    @WithUserDetails(value = "test")
    @DisplayName(value = "계정 비활성화/삭제")
    void turnOffAccountTest() throws Exception {

        mockMvc.perform(delete("/api/members"))
                .andExpect(status().isNoContent())
                .andDo(print());

        Member findMember = memberRepository.findByUsername("test").orElseThrow(
                () -> new MemberNotFoundException("존재하지 않는 멤버")
        );

        assertThat(findMember.isActivated()).isFalse();
    }

    @Test
    @WithUserDetails(value = "test")
    @DisplayName(value = "로그인 정보 조회")
    void getMyInfoTest() throws Exception {


        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}