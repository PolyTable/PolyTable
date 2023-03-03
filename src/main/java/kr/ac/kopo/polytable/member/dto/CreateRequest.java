package kr.ac.kopo.polytable.member.dto;

import kr.ac.kopo.polytable.member.model.Member;
import kr.ac.kopo.polytable.member.model.Store;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateRequest {

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private String name;

    @NotNull
    private String telNo;

    @NotNull
    private Store store;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .username(username)
                .password(password)
                .roleType(RoleType.USER)
                .name(name)
                .birthDate(birthDate)
                .telNo(telNo)
                .store(store)
                .build();
    }
}
