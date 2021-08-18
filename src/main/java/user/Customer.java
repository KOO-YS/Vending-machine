package user;

import error.InvalidProductIdException;
import error.NotSufficientMoneyException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import sale.Product;

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
     * TODO 자판기에서 음료 선택
     * @param idx 상품 번호
     */
    public Product chooseProduct(int idx) {
        Product choose;
        // 유효한 상품 번호인지 체크
        if ((choose = vendingMachine.getDetail(idx)) != null){
           if (vendingMachine.isChangeAvailable(choose))
               return choose;
        } else throw new InvalidProductIdException();
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
