package product;

import error.StockOutOfBoundsException;
import lombok.Builder;

/**
 * Vending Machine 내부 각 상품 칸
 */
@Builder
public class StockImpl implements Stock{
    static private final int COUNT_MAX = 10;      // 자판기 재고 최대치

    private Product product;        // 상품
    private int count;              // 재고

    public StockImpl(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    @Override
    public Stock makeStock(Product t, int count) {
        return new StockImpl(t, count);
    }

    /**
     * 자판기에 재고를 등록한다
     * 만약 기존에 존재하는 물품이라면 수량만 변경한다
     * @param add 등록 수량
     */
    @Override
    public void stockUp(int add) {
        int total = add + count;
        if (add <=0 || add > COUNT_MAX)
            throw new StockOutOfBoundsException(add);
        count = total;
    }

    /**
     * 자판기에 물품 재고를 제거한다
     */
    @Override
    public int stockOut() {
        return --count;
    }

    @Override
    public void stockAllOut(Product product) {
        count = 0;
    }

    @Override
    public int getStock() {
        return count;
    }

    @Override
    public Product getDetail() {
        return product;
    }

    @Override
    public void updateDetail(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return product.toString()+" : "+count+"개";
    }
}
