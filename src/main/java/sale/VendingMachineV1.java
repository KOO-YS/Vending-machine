package sale;

import error.InvalidProductIdException;
import error.LackOfStockException;
import error.StockOutOfBoundsException;
import product.Product;
import sale_v2.Machine;
import product.Stock;
import product.StockImpl;

import java.util.*;

public class VendingMachineV1 implements Machine {
    private static final VendingMachineV1 singleton = new VendingMachineV1();

    public static VendingMachineV1 getInstance() {
        return singleton;
    }


    private final Map<Product, Integer> productMap;     // 상품 리스트 & 재고량
    private int balance;          // 자판기에 충전된 돈

    private VendingMachineV1() {
        productMap = new HashMap<>();
        this.balance = 0;

        // init setting
        Product[] sample = {
                Product.builder().idx(1).name("코카콜라").price(1200).build(),
                Product.builder().idx(2).name("사이다").price(1500).build(),
                Product.builder().idx(3).name("환타").price(500).build(),
                Product.builder().idx(4).name("다이제").price(200).build(),
        };
        for (int i=0; i<sample.length; i++){
            productMap.put(sample[i], i+2);
        }
    }

    public int getStock(int idx) {
        Product product = getDetail(idx);
        return productMap.getOrDefault(product, 0);
    }

    public int getBalance() {
        return this.balance;
    }

    public void chargeBalance(int money) {
        this.balance += money;
    }

    /**
     * 충전된 금액으로 물품을 구매 가능여부를 판단한다.
     */
    public boolean isEnoughMoney(int idx) {
        Product product = getDetail(idx);

        if(product == null) throw new InvalidProductIdException();

        if (balance - product.getPrice() <0) return false;
        else {
            this.balance -= product.getPrice();
            stockOut(idx);
            return true;
        }
    }

    public Product getDetail(int idx) {
        Product product = null;
        for(Product p : productMap.keySet()) {
            if(p.getIdx() == idx) product = p;
        }
        if(product == null) throw new InvalidProductIdException();
        return product;
    }

    public void makeStock(Product p, int count) {
        if (count <=0 || count > STOCK_MAX) throw new StockOutOfBoundsException(count);
        productMap.put(p, count);
    }

    /**
     * 자판기에 재고를 등록한다
     * 만약 기존에 존재하는 물품이라면 수량만 변경한다
     * @param idx 등록 상품 번호
     * @param stock 등록 수량
     */
    public void stockUp(int idx, int stock) {
        // 재고 수량 확인 (기존 + 추가)
        stock += getStock(idx);

        // 추가 가능한 재고일 때, 자판기에 물품 등록
        if (stock <=0 || stock > STOCK_MAX) throw new StockOutOfBoundsException(stock);
        productMap.put(getDetail(idx), stock);
    }

    /**
     * 자판기에 물품 재고를 1개 제거한다
     * @param idx
     */
    public void stockOut(int idx) {
        Product product = getDetail(idx);

        int stock = getStock(idx);
        stock -= 1;
        if (stock < 0)
            throw new LackOfStockException();
//        else if(stock==0)
//            productMap.remove(product);
        else
            productMap.put(product, stock-1);
    }

    /**
     * 상품을 모두 제거한다
     */
    public void stockAllOut(int idx) {
        productMap.remove(getDetail(idx));
    }


    /**
     * 충전된 돈을 반환한다
     */
    public int refundChange() {
        int refund = this.balance;
        this.balance = 0;
        return refund;
    }

    public Map<Integer, Stock> stockIterator() {   // index + stock
        Map<Integer, Stock> map = new HashMap<>();
        for (Product p : productMap.keySet()) {
            map.put(p.getIdx(), StockImpl.builder().product(p).count(productMap.get(p)).build());
        }
        return map;
    }

}
