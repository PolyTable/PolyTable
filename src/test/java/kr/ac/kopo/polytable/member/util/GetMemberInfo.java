package kr.ac.kopo.polytable.member.util;

import kr.ac.kopo.polytable.member.dto.CreateRequest;
import kr.ac.kopo.polytable.member.dto.ModifiedRequest;
import kr.ac.kopo.polytable.member.dto.RoleType;
import kr.ac.kopo.polytable.member.model.Address;
import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.Store;

import java.time.LocalDate;
import java.time.LocalTime;

public class GetMemberInfo {


    public static CreateRequest bingingMember() {

        /**
         * 유저 기본 정보
         */

        final String email = "test@email.com";
        final String username = "test";
        final String password = "test";
        final LocalDate birthDate = LocalDate.of(2000,1,1);
        final String name = "testName";
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

        return CreateRequest.builder()
                .email(email)
                .username(username)
                .password(password)
                .birthDate(birthDate)
                .name(name)
                .telNo(telNo)
                .store(store)
                .build();
    }

    public static CreateRequest initMember() {

        /**
         * 유저 기본 정보
         */

        final String email = "test2@email.com";
        final String username = "test2";
        final String password = "test2";
        final LocalDate birthDate = LocalDate.of(2000,1,1);
        final String name = "testName2";
        final String telNo = "000-0001-1111";

        /**
         * 가게 기본 정보
         */

        final String crn = "000-11-00011";
        final String storeName = "testName2";
        final String storeTelNo = "000-111-0011";
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

        return CreateRequest.builder()
                .email(email)
                .username(username)
                .password(password)
                .birthDate(birthDate)
                .name(name)
                .telNo(telNo)
                .store(store)
                .build();
    }

    public static ModifiedRequest modifiedMemberInfo() {
        /**
         * 유저 수정 정보
         */

        final String email = "test@email.com33";
        final String password = "test33";
        final LocalDate birthDate = LocalDate.of(2000,1,1);
        final String name = "testName33";
        final String telNo = "000-1111-1111";

        /**
         * 가게 수정 정보
         */

        final String crn = "000-11-11111";
        final String storeName = "testName33";
        final String storeTelNo = "000-111-1111";
        final LocalDate foundedDate = LocalDate.of(2023,1,1);
        final LocalTime openTime = LocalTime.of(7,0);
        final LocalTime closeTime = LocalTime.of(15,30);

        /**
         * 주소 수정 정보
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

        return ModifiedRequest.builder()
                .email(email)
                .password(password)
                .birthDate(birthDate)
                .name(name)
                .telNo(telNo)
                .store(store)
                .build();
    }
}
