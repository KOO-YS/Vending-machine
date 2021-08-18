package user;

import error.NotSufficientMoneyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sale.Product;
import sale.VendingMachine;

import java.util.Iterator;

public class CustomerTests {

    private static VendingMachine vendingMachine;
    private static Customer haveMoney;
    private static Customer defaultMoney;
    private static Customer noMoney;

    @BeforeAll
    public static void setUp() {
        vendingMachine = VendingMachine.getInstance();
        
        // Customer 설정
        haveMoney = Customer.builder()
                        .budget(10000)
                        .build();

        defaultMoney = Customer.builder().build();

        noMoney = Customer.builder()
                .budget(0)
                .build();
    }

    /**
     * 예산에 맞게 돈을 충전하려는 경우
     */
    @Test
    public void charge() {
        // given
        int money = 5000;

        // when
        haveMoney.chargeBalance(money);     // 10000 - 5000

        // then
        // 자판기에 충전된 돈
        Assertions.assertEquals(vendingMachine.getBalance(), 5000);
        // 사용자에게 남은 돈
        Assertions.assertEquals(haveMoney.getBudget(), 5000);
    }

    /**
     * 예산을 초과해서 돈을 충전하려는 경우
     */
    @Test
    public void chargeOver() {
        // given
        int money = 500000;  // 10000 - 50000 ?

        // when & then
        Assertions.assertThrows(NotSufficientMoneyException.class, () -> defaultMoney.chargeBalance(money));

        // then
        Assertions.assertEquals(vendingMachine.getBalance(), 0);
    }

    /**
     * 상품을 구매한 경우
     */
    @Test
    public void pick() {
        // given
        haveMoney.chargeBalance(10000);

        // when
        Iterator i = vendingMachine.productIterator();
        int total = 0;
        while(i.hasNext()) {
            Product product = (Product)(i.next());
            haveMoney.chooseProduct(product.getIdx());
            total += product.getPrice();
        }
        Assertions.assertEquals(haveMoney.refundChange(), 10000-total);
    }

    /**
     * 돈이 없는 소비자가 자판기에 충전하려고 한 경우
     */
    @Test
    public void setNoMoney() {
        Assertions.assertThrows(NotSufficientMoneyException.class, () -> noMoney.chargeBalance(1000));
    }

    /**
     * 충전된 돈보다 구매할려는 물품이 더 비싼 경우
     */
    @Test
    public void setOverMoney() {
//        Assertions.assertThrows()
    }
    
    @Test
    public void chooseProduct() {
        haveMoney.chargeBalance(5000);

        Iterator i = vendingMachine.productIterator();
        while(i.hasNext()) {
            System.out.println(i.next());
        }

        Product p = haveMoney.chooseProduct(2);
        System.out.println("구매 "+p);
    }

}
