package kr.ac.kopo.polytable.auth.dto;

import kr.ac.kopo.polytable.global.jwt.vo.AccessToken;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleAuthResponse {

    private AccessToken accessToken;
    private boolean result;

    public static SimpleAuthResponse from(final AccessToken accessToken, final boolean result) {
        return new SimpleAuthResponse(accessToken, result);
    }
}
