package kr.ac.kopo.polytable.global.jwt.error;

import kr.ac.kopo.polytable.global.error.exception.BusinessException;
import kr.ac.kopo.polytable.global.error.exception.ErrorCode;

public class TokenNotFoundException extends BusinessException {
    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
