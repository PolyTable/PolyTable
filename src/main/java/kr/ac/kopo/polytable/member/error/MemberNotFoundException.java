package kr.ac.kopo.polytable.member.error;

import kr.ac.kopo.polytable.global.error.exception.ErrorCode;

import javax.persistence.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(String.valueOf(errorCode));
    }
}
