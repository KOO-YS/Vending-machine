package user;

import error.NotSufficientMoneyException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import product.Product;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
public class Customer extends User{

    @Builder.Default
    private int budget = 5000;     // 사용가능 자금
    @Builder.Default
    private List<Product> belong = new ArrayList<>();

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
     * @param idx 상품 번호
     */
    public Product chooseProduct(int idx) {
        Product choose = null;
        if(vendingMachine.isEnoughMoney(idx)) {
            choose = vendingMachine.getDetail(idx);
            vendingMachine.stockOut(idx);
        }
        else throw new NotSufficientMoneyException();
        return choose;
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
