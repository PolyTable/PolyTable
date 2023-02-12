package kr.ac.kopo.polytable.member.auth.error;

import kr.ac.kopo.polytable.global.error.exception.BusinessException;
import kr.ac.kopo.polytable.global.error.exception.ErrorCode;

public class MemberInfoMismatchException extends BusinessException {

    public MemberInfoMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
