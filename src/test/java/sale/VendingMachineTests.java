package sale;

import org.junit.jupiter.api.Test;

public class VendingMachineTests {

    private static VendingMachine vendingMachine = VendingMachine.getInstance();

    @Test
    public void add() {
        Product product = new Product(1, "코카콜라", 1200);
        vendingMachine.stockUp(product, 10);
        vendingMachine.stockOut(product, 10);
    }

    /*
     * 올바르게 구매를 했을 경우
     * -> [돈 넣고, 상품 선택하고, 상품 받고]
     * */

    /*
     * 두 개 이상의 상품을 구매한 경우
     * */

    /*
     * 구매하려는 상품보다 충전한 돈이 부족한 경우
     * */

    /*
     * 재고가 없는 상품을 구매할려는 경우
     * */

    /*
     * 돈 환불을 요청한 경우
     * */
}
