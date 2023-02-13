package kr.ac.kopo.polytable.member.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifiedRequest {

    private String email;
    private String password;
    private LocalDate birthDate;
    private String name;
    private String telNo;
}
