package user;

import error.InvalidProductIdException;
import error.LackOfStockException;
import error.NotSufficientMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import product.Product;
import sale_v2.Machine;
import product.Stock;
import sale_v2.VendingMachineV2;

import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

//https://www.vogella.com/tutorials/JUnit/article.html#unittesting_junitexample

public class CustomerTests {

    private static Machine vendingMachine;
    Customer haveMoney;
    Customer defaultMoney;
    Customer noMoney;

    @BeforeEach
    void setUp() {
        vendingMachine = VendingMachineV2.getInstance();
        // Customer 설정
        haveMoney = Customer.builder()
                        .budget(10000)
                        .build();
        haveMoney.setVendingMachine(vendingMachine);

        defaultMoney = Customer.builder().build();
        defaultMoney.setVendingMachine(vendingMachine);

        noMoney = Customer.builder()
                .budget(0)
                .build();
        noMoney.setVendingMachine(vendingMachine);
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
        Map<Integer, Stock> map = vendingMachine.stockIterator();
        Iterator<Integer> i = map.keySet().iterator();
        int total = 0;
        while(i.hasNext()) {
            int index = i.next();
            Product product = map.get(index).getDetail();
            haveMoney.chooseProduct(index);
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
        try {
            for (int i=0; i<10; i++) {
                haveMoney.chooseProduct(1);     // 재고 소진
            }
        } catch (LackOfStockException | InvalidProductIdException e) {
            System.out.println("확인");
            assertTrue(true);
        }
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
        assertThrows(NotSufficientMoneyException.class, () -> haveMoney.chooseProduct(3));
        // then
    }

    /**
     * 구매 과정을 출력
     */
    @Test
    void printProduct() {
        haveMoney.chargeBalance(5000);

        printMachine();

        Product p = haveMoney.chooseProduct(2);
        System.out.println("구매 "+p);
    }
    private static boolean printMachine() {
        Map<Integer, Stock> map = vendingMachine.stockIterator();
        Iterator<Integer> i = map.keySet().iterator();
        if (!i.hasNext()) {
            System.out.println("선택 가능한 물품이 없습니다");
            return false;
        }
        while(i.hasNext()) {
            int index = (int)i.next();
            System.out.println(index+"번 "+map.get(index));
        }
        return true;
    }
}
