package kr.ac.kopo.polytable.member.dto;

import kr.ac.kopo.polytable.member.model.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

    private String email;
    private String username;
    private String name;
    private int age;
    private String telNo;

    public static MemberResponse of(final Member member) {
        int memberYear = member.getBirthDate().getYear();
        int now = LocalDate.now().getYear();
        int age = now - memberYear;

        return new MemberResponse(
                member.getEmail(),
                member.getUsername(),
                member.getName(),
                age,
                member.getTelNo());
    }
}
