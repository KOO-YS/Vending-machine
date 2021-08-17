package error;

public class StockOutOfBoundsException extends RuntimeException {
    public StockOutOfBoundsException(int stock) {
        super("등록 가능한 재고 수량이 아닙니다. \t(총 "+ stock +"개)");
    }
}
