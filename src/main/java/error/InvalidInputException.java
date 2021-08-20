package error;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("잘못된 입력 값입니다");
    }
}
