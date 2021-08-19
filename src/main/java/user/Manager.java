package user;

import lombok.Builder;
import product.Product;

@Builder
public class Manager extends User{

    public Manager() { }

    public void makeStock(Product product, int count) {
        vendingMachine.makeStock(product, count);
    }
    /**
     * 상품 수량 추가
     * @param idx 추가할 상품
     * @param stock 추가 수량
     */
    public void stockUp(int idx, int stock) {
        vendingMachine.stockUp(idx, stock);
    }

    /**
     * 상품 아예 제거
     * @param idx 제거할 상품 번호
     */
    public void stockOut(int idx) {
        vendingMachine.stockAllOut(idx);
    }

    /**
     * 상품의 금액을 변경
     * @param idx 변경 대상 번호
     * @param updatePrice 변경할 금액
     */
    public void updateProduct(int idx, int updatePrice){
        int existed = vendingMachine.getStock(idx);
        // 기존 내용 제거
        vendingMachine.stockOut(idx);
        // 업데이트 내용 추가
        Product product = vendingMachine.getDetail(idx);
        product.setPrice(updatePrice);

        stockUp(idx, existed);
    }

}
