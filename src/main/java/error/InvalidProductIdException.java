package error;

public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException() {
        super("유효한 상품 번호가 아닙니다.");
    }
}
