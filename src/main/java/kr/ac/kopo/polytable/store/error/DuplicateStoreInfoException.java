package kr.ac.kopo.polytable.store.error;

public class DuplicateStoreInfoException extends RuntimeException{
    public DuplicateStoreInfoException(String message) {
        super(message);
    }
}
