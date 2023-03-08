package kr.ac.kopo.polytable.auth.application;

import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetailsService;
import kr.ac.kopo.polytable.member.dto.CreateRequest;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.member.util.GetMemberInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @MockBean(name = "memberRepository")
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManagerBuilder managerBuilder;

    @Test
    @DisplayName(value = "권한 정보 저장")
    void saveInfoIntoSecurityContextTest() {
        CreateRequest createRequest = GetMemberInfo.bingingMember();
        Member member = createRequest.toEntity();

        String encodedPassword = passwordEncoder.encode(member.getPassword());

        given(memberRepository.save(member)).willReturn(member);
        Member savedMember = memberRepository.save(member);

        CustomUserDetails setDetails = CustomUserDetails.of(savedMember);

        given(memberRepository.findUserDetailsByUsername(member.getUsername()))
                .willReturn(Optional.of(setDetails));

        CustomUserDetails userDetails = memberRepository.findUserDetailsByUsername(savedMember.getUsername())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 멤버"));


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, encodedPassword);

        Authentication authentication = managerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        assertThat(member.getUsername()).isEqualTo(principal.getUsername());
        assertThat(principal.getPassword()).isEqualTo("password");
    }

    @Test
    @DisplayName(value = "토큰 발급 값 확인")
    void tokenValueCheckFromTokenProvider() {

    }

    @Test
    @DisplayName(value = "리프레시 토큰 유효 확인")
    void checkExpiredOfRefreshToken() {

    }
}