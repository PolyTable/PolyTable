package kr.ac.kopo.polytable.member.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.ac.kopo.polytable.global.security.principal.CustomUserDetails;
import kr.ac.kopo.polytable.member.dto.ModifiedRequest;
import kr.ac.kopo.polytable.member.error.MemberNotFoundException;
import kr.ac.kopo.polytable.member.model.Address;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.Store;
import kr.ac.kopo.polytable.member.model.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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
    void createMemberAndStoreTest() throws Exception {
        ObjectMapper om = new ObjectMapper();

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
                .name(name)
                .telNo(telNo)
                .store(store)
                .build();

        String body = om.registerModule(new JavaTimeModule()).writeValueAsString(member);

        mockMvc.perform(post("/api/public/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "test")
    void modifiedMemberAndStoreTest() throws Exception {

        ObjectMapper om = new ObjectMapper();

        /**
         * 유저 수정 정보
         */

        final String email = "newTest@email.com";
        final String password = "testPassword";
        final LocalDate birthDate = LocalDate.of(2010,1,1);
        final String name = "newTestName";
        final String telNo = "111-1111-1111";

        /**
         * 가게 기본 정보
         */

        final String crn = "111-00-00000";
        final String storeName = "newTestName";
        final String storeTelNo = "111-000-0000";
        final LocalDate foundedDate = LocalDate.of(2015,3,1);
        final LocalTime openTime = LocalTime.of(9,0);
        final LocalTime closeTime = LocalTime.of(17,30);

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

        ModifiedRequest request = ModifiedRequest.builder()
                .email(email)
                .password(password)
                .birthDate(birthDate)
                .name(name)
                .telNo(telNo)
                .store(store)
                .build();

        String body = om.registerModule(new JavaTimeModule()).writeValueAsString(request);

        mockMvc.perform(put("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = "test")
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
    void principalCheckTest() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        final String username = userDetails.getUsername();

        Member findMember = memberRepository.findByUsername(username).orElseThrow(
                () -> new MemberNotFoundException("존재하지 않는 멤버")
        );

        assertThat(findMember.getUsername()).isEqualTo(username);
    }

    @Test
    @WithUserDetails(value = "test")
    void getMyInfoTest() throws Exception {

        mockMvc.perform(get("/api/members"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}