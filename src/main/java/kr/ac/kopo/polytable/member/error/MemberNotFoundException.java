package kr.ac.kopo.polytable.member.error;


public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message) {
        super(message);
    }
}
