package user;

import error.LackOfStockException;
import error.NotSufficientMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import product.Product;
import sale.VendingMachine;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

//https://www.vogella.com/tutorials/JUnit/article.html#unittesting_junitexample

public class CustomerTests {

    private static final VendingMachine vendingMachine = VendingMachine.getInstance();
    Customer haveMoney;
    Customer defaultMoney;
    Customer noMoney;

    @BeforeEach
    void setUp() {
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
    void charge() {
        // given
        int money = 5000;

        // when
        haveMoney.chargeBalance(money);     // 10000 - 5000

        // then
        // 자판기에 충전된 돈
        assertEquals(vendingMachine.getBalance(), 5000);
        // 사용자에게 남은 돈
        assertEquals(haveMoney.getBudget(), 5000);
    }

    /**
     * 예산을 초과해서 돈을 충전하려는 경우
     */
    @Test
    void chargeOver() {
        // given
        int money = 500000;  // 10000 - 50000 ?

        // when & then
        assertThrows(NotSufficientMoneyException.class, () -> defaultMoney.chargeBalance(money));

        // then
        assertEquals(vendingMachine.getBalance(), 0);
    }

    /**
     * 상품을 구매하고 남은 돈을 환불받은 경우
     */
    @Test
    void chooseProduct() {
        // given
        haveMoney.chargeBalance(10000);

        // when
        Iterator i = vendingMachine.stockIterator();
        int total = 0;
        while(i.hasNext()) {
            Product product = (Product)(i.next());
            haveMoney.chooseProduct(product.getIdx());
            total += product.getPrice();
        }
        assertEquals(haveMoney.refundChange(), 10000-total);
    }

    /**
     * 재고를 초과해서 상품을 구매한 경우
     */
    @Test
    void chooseOverProduct() {
        // given
        haveMoney.chargeBalance(10000);

        // when
        haveMoney.chooseProduct(1);     // 재고 소진
        
        // then
        assertThrows(LackOfStockException.class, () -> haveMoney.chooseProduct(1));
    }

    /**
     * 돈이 없는 소비자가 자판기에 충전하려고 한 경우
     */
    @Test
    void setNoMoney() {
        assertThrows(NotSufficientMoneyException.class, () -> noMoney.chargeBalance(1000));
    }

    /**
     * 충전된 돈보다 구매할려는 물품이 더 비싼 경우
     * 상품을 가져오지 못한다
     */
    @Test
    void setOverMoney() {
        // given
        haveMoney.chargeBalance(100);
        // when
        Product product = haveMoney.chooseProduct(3);
        // then
        assertNull(product);
    }

    /**
     * 구매 과정을 출력
     */
    @Test
    void printProduct() {
        haveMoney.chargeBalance(5000);

        Iterator i = vendingMachine.stockIterator();
        while(i.hasNext()) {
            System.out.println(i.next());
        }

        Product p = haveMoney.chooseProduct(2);
        System.out.println("구매 "+p);
    }

}
