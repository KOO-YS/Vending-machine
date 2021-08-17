package user;

import sale.Product;
import sale.VendingMachine;

public class Manager {

    private VendingMachine vendingMachine;

    public Manager() {
        this.vendingMachine = VendingMachine.getInstance();
    }

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
    public Product updateProduct(Product product, int updatePrice){
        product.setPrice(updatePrice);
        return product;
    }

}
