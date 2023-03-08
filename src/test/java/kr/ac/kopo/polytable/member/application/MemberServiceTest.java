package kr.ac.kopo.polytable.member.application;

import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetailsService;
import kr.ac.kopo.polytable.member.dto.*;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.member.util.GetMemberInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean(name = "memberRepository")
    private MemberRepository memberRepository;

    @MockBean(name = "customUserDetailsService")
    private CustomUserDetailsService customUserDetailsService;


    @Test
    @DisplayName(value = "회원가입")
    void createMemberTest() {

        CreateRequest member = GetMemberInfo.bingingMember();

        given(memberRepository.save(member.toEntity())).willReturn(member.toEntity());
        SimpleMemberResponse response = memberService.create(member.toEntity());


        assertThat(response.getUsername()).isEqualTo(member.getUsername());
        assertThat(response.getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("회원정보_수정")
    void modifiedMemberInfoTest() {

        CreateRequest member = GetMemberInfo.bingingMember();

        given(memberRepository.save(member.toEntity())).willReturn(member.toEntity());
        Member savedMember = memberRepository.save(member.toEntity());

        ModifiedRequest request = GetMemberInfo.modifiedMemberInfo();
        savedMember.changeNewInfo(request);

        assertThat(savedMember.getEmail()).isEqualTo(request.getEmail());
        assertThat(savedMember.getName()).isEqualTo(request.getName());
        assertThat(savedMember.getTelNo()).isEqualTo(request.getTelNo());
        assertThat(savedMember.getStore().getStoreName()).isEqualTo(request.getStore().getStoreName());
    }

    @Test
    @DisplayName(value = "회원 탈퇴/비활성화")
    void turnOffAccountTest() {

        CreateRequest member = GetMemberInfo.bingingMember();

        given(memberRepository.save(member.toEntity())).willReturn(member.toEntity());

        Member savedMember = memberRepository.save(member.toEntity());

        savedMember.turnOffAccount();

        assertThat(savedMember.isActivated()).isFalse();
    }

    @Test
    @DisplayName(value = "로그인 계정 정보")
    void trackingAuthTest() {

        CreateRequest member = GetMemberInfo.bingingMember();
        given(memberRepository.save(member.toEntity())).willReturn(member.toEntity());
        Member savedMember = memberRepository.save(member.toEntity());


        given(customUserDetailsService.loadUserByUsername(savedMember.getUsername())).willReturn(CustomUserDetails.of(member.toEntity()));

        CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(member.getUsername());

        given(memberRepository.findById(customUserDetails.getId())).willReturn(Optional.of(member.toEntity()));

        MemberResponse findMember = memberRepository.findById(customUserDetails.getId())
                .map(MemberResponse::of).orElseThrow(
                () -> new MemberNotFoundException("존재하지 않는 멤버")
        );

        assertThat(findMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(findMember.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(findMember.getName()).isEqualTo(savedMember.getName());
    }
}