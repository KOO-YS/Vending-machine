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
    private static Customer customer1;
    private static Customer customer2;

    @BeforeAll
    public static void setUp() {
        vendingMachine = VendingMachine.getInstance();
        customer1 = new Customer(10000);
        customer2 = new Customer(10000);
    }

    /**
     * 예산에 맞게 돈을 충전하려는 경우
     * 예산을 초과해서 돈을 충전하려는 경우
     */
    @Test
    public void charge() {
        // given
        int money1 = 5000;
        int money2 = 500000;

        // when
        customer1.chargeBalance(money1);
        Assertions.assertThrows(NotSufficientMoneyException.class, () ->customer2.chargeBalance(money2));

        // then
        Assertions.assertEquals(vendingMachine.getBalance(), 5000);
    }

    @Test
    public void chooseProduct() {
        Iterator i = vendingMachine.productIterator();
        while(i.hasNext()) {
            System.out.println(i.next());
        }
        customer1.chooseProduct(2);
    }

}
