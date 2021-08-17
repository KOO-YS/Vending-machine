package error;

public class NotSufficientMoneyException extends RuntimeException {
    public NotSufficientMoneyException() {
        super("예산이 충분하지 않습니다");
    }
}
