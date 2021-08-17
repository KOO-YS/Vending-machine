package error;

public class LackOfStockException extends RuntimeException {
    public LackOfStockException() {
        super("재고의 수량이 부족합니다");
    }
}
