package kr.ac.kopo.polytable.member.dto;

import kr.ac.kopo.polytable.member.model.Store;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifiedRequest {

    /**
     * 해당 요청의 모든 값은 null 값을 허용하지 않으나 예외적으로 비밀번호는 null 값의 전송을 허용합니다.
     * 대부분의 서비스 방식으로 미루어보아 개인정보 수정페이지에 진입시 다른 항목은 기존의 정보를 채워넣지만
     * 비밀번호 항목은 비워놓는 것을 볼 수 있습니다.
     * 따라서, 비밀번호는 빈칸으로 두되 변경없이 null 값으로 전송되는 경우 기존의 비밀번호를
     * 그대로 유지하는 것으로 하며 그 외의 다른 값들은 사용자의 입력이 없을 시 기존의 값을 유지하여
     * 전송하도록 한다면 코드의 길이가 짧아지고 문제없이 수행 가능합니다.
     */

    @NotNull
    private String email;
    private String password;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private String name;
    @NotNull
    private String telNo;
    @NotNull
    private Store store;

    public void setPassword(String password) {
        this.password = password;
    }
}
