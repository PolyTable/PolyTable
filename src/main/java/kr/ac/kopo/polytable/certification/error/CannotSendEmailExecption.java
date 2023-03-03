package kr.ac.kopo.polytable.certification.error;

import org.springframework.mail.MailSendException;

public class CannotSendEmailExecption extends MailSendException {
    public CannotSendEmailExecption(String msg) {
        super(msg);
    }
}
