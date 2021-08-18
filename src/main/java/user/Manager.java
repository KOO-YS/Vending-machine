package user;

import lombok.Builder;
import sale.Product;

@Builder
public class Manager extends User{

    public Manager() { }

    /**
     * 상품 추가
     * @param product 추가할 상품
     * @param stock 추가 수량
     */
    public void stockUp(Product product, int stock) {
        vendingMachine.stockUp(product, stock);
    }

    /**
     * 상품 제거
     * @param product 제거할 상품
     */
    public void stockOut(Product product) {
        vendingMachine.stockOut(product);
    }

    /**
     * 상품의 금액을 변경
     * @param product 변경 대상
     * @param updatePrice 변경할 금액
     */
    public void updateProduct(Product product, int updatePrice){
        product = vendingMachine.getDetail(product.getIdx());
        int existed = vendingMachine.getStock(product);
        // 기존 내용 제거
        vendingMachine.stockOut(product);
        // 업데이트 내용 추가
        product.setPrice(updatePrice);
        vendingMachine.stockUp(product, existed);
    }

}
