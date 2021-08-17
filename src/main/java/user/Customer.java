package user;

import error.NotSufficientMoneyException;
import lombok.Getter;
import sale.Product;
import sale.VendingMachine;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Customer {
    private VendingMachine vendingMachine;
    private int budget;     // 사용가능 자금
    private List<Product> belong;

    public Customer(int budget) {
        this.vendingMachine = VendingMachine.getInstance();
        this.budget = budget;
        belong = new ArrayList<>();
    }

    /**
     * 자판기에 금액을 충전한다
     * @param money 충천 금액
     */
    public void chargeBalance(int money) {
        if (budget - money < 0) throw new NotSufficientMoneyException();
        this.budget -= money;
        vendingMachine.chargeBalance(money);
    }

    /**
     * TODO 자판기에서 음료 선택
     * @param idx 상품 번호
     */
    public Product chooseProduct(int idx) {
        // 유효한 상품 번호인지 체크
        vendingMachine.isChangeAvailable(new Product(idx));

        // 재고 & 금액 확인

//        Product get = vendingMachine.

        return null;
    }

    /**
     * 충전된 금액을 반환받는다
     */
    public int refundChange() {
        int refund = vendingMachine.refundChange();
        this.budget += refund;
        return refund;
    }

}
