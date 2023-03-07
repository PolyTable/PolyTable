package kr.ac.kopo.polytable.member.application;

import kr.ac.kopo.polytable.member.dto.RoleType;
import kr.ac.kopo.polytable.member.dto.SimpleMemberResponse;
import kr.ac.kopo.polytable.member.model.Address;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.Store;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import kr.ac.kopo.polytable.modelmapper.CustomModelMapper;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

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


    @Test
    void createMemberTest() {
        /**
         * 유저 기본 정보
         */

        final String email = "test@email.com22";
        final String username = "test22";
        final String password = "test22";
        final LocalDate birthDate = LocalDate.of(2000,1,1);
        final String name = "testName22";
        final String telNo = "000-0000-1111";

        /**
         * 가게 기본 정보
         */

        final String crn = "000-11-00000";
        final String storeName = "testName22";
        final String storeTelNo = "000-111-0000";
        final LocalDate foundedDate = LocalDate.of(2023,1,1);
        final LocalTime openTime = LocalTime.of(8,0);
        final LocalTime closeTime = LocalTime.of(18,30);

        /**
         * 주소 기본 정보
         */

        final String province = "대전광역시";
        final String city = "동구";
        final String road = "우암로";
        final String zipcode = "352-21";

        Address address = Address.builder()
                .province(province)
                .city(city)
                .road(road)
                .zipcode(zipcode)
                .build();

        Store store = Store.builder()
                .crn(crn)
                .storeName(storeName)
                .storeTelNo(storeTelNo)
                .foundedDate(foundedDate)
                .openTime(openTime)
                .closeTime(closeTime)
                .address(address)
                .build();

        Member member = Member.builder()
                .email(email)
                .username(username)
                .password(password)
                .birthDate(birthDate)
                .roleType(RoleType.USER)
                .name(name)
                .telNo(telNo)
                .store(store)
                .build();


        given(memberRepository.save(member)).willReturn(member);
        SimpleMemberResponse response = memberService.create(member);


        assertThat(response.getUsername()).isEqualTo(member.getUsername());
        assertThat(response.getName()).isEqualTo(member.getName());
    }

    @Test
    void modifiedMemberInfoTest() {

    }
}