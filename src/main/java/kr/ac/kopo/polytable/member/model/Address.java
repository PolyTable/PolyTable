package kr.ac.kopo.polytable.member.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Address {

    private String province;
    /**
     * 특별,광역시의 경우 도시가 아닌 도 취급 / 도시는 구를 입력
     * 단, 제주도 또는 울릉도는 하나의 도로 취급하고 서귀포시, 울릉군을 도시로 입력.
     */
    private String city;
    private String road;
    private String zipcode;
}
