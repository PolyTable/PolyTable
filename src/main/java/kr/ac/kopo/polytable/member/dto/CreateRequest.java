package kr.ac.kopo.polytable.member.dto;

import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.vo.RoleType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateRequest {


    private String email;
    private String username;
    private String password;
    private LocalDate birthDate;
    private String name;
    private String telNo;


    public Member toEntity() {
        return Member.builder()
                .email(email)
                .username(username)
                .password(password)
                .roleType(RoleType.USER)
                .name(name)
                .birthDate(birthDate)
                .telNo(telNo)
                .build();
    }
}
