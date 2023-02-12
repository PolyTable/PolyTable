package kr.ac.kopo.polytable.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @JsonProperty("username")
    @Valid
    @NotNull(message = "필수 값입니다.")
    private String username;

    @JsonProperty("password")
    @Valid
    @NotNull(message = "필수 값입니다.")
    private String password;

    public static LoginRequest of(final String username, final String password) {
        return new LoginRequest(username, password);
    }
}
