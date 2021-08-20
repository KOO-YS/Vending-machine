package user;


import error.StockOutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import product.Product;
import sale.VendingMachineV1;
import sale_v2.Machine;
import product.Stock;

import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTests {

    private static Machine vendingMachine;
    Manager manager;

    @BeforeEach
    void setUp() {
        vendingMachine = VendingMachineV1.getInstance();        // 머신 변경
        // Manager 설정
        manager = Manager.builder().build();
        manager.setVendingMachine(vendingMachine);

    }

    /**
     * 상품 생성
     */
    @Test
    void makeStock() {
        // given
        Product product = Product.builder().idx(1).name("물건1").price(1000).build();

        // when
        manager.makeStock(product, 10);

        // then
        printMachine();

    }
    @Test
    void makeOverStock() {
        Product product = Product.builder().idx(1).name("물건1").price(1000).build();

        assertThrows(StockOutOfBoundsException.class, () -> manager.makeStock(product, 100), "초과 재고");
        assertThrows(StockOutOfBoundsException.class, () -> manager.makeStock(product, 0), "최소 재고 X");
    }
    /**
     * 관리자의 상품 제거는 전체 제거
     */
    @Test
    void stockOut() {
        // when
        manager.stockOut(1);

        // then
        printMachine();
    }

    @Test
    void updateProduct() {
        // given
        int index = 1;
        int update = 1000000;

        // when
        manager.updateProduct(index, update);

        // then
        assertEquals(vendingMachine.getDetail(index).getPrice(), update);
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
