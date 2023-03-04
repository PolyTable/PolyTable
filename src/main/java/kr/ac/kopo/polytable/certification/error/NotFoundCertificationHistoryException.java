package kr.ac.kopo.polytable.certification.error;

public class NotFoundCertificationHistoryException extends RuntimeException{
    public NotFoundCertificationHistoryException(String message) {
        super(message);
    }
}
