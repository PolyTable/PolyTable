package kr.ac.kopo.polytable.global.util;

import kr.ac.kopo.polytable.member.application.MemberService;
import kr.ac.kopo.polytable.member.dto.CreateRequest;
import kr.ac.kopo.polytable.member.model.Address;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class InitDummyUser {

    private final MemberService memberService;

    @PostConstruct
    public void init() {
        /**
         * 유저 기본 정보
         */

        final String email = "test@email.com";
        final String username = "test";
        final String password = "test";
        final LocalDate birthDate = LocalDate.of(2000,1,1);
        final String name = "testName";
        final String telNo = "000-0000-0000";

        /**
         * 가게 기본 정보
         */

        final String crn = "000-00-00000";
        final String storeName = "testName";
        final String storeTelNo = "000-000-0000";
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

        CreateRequest request = CreateRequest.builder()
                .email(email)
                .username(username)
                .password(password)
                .birthDate(birthDate)
                .name(name)
                .telNo(telNo)
                .store(store)
                .build();


        memberService.create(request.toEntity());
    }
}
