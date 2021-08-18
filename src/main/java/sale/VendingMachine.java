package sale;

import error.LackOfStockException;
import error.StockOutOfBoundsException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VendingMachine {

    private Map<Product, Integer> productMap;     // 상품 리스트 & 재고량
    private int balance;          // 자판기에 충전된 돈

    static private final int STOCK_MAX = 10;      // 자판기 재고 최대치

    private VendingMachine() {
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

    private static VendingMachine singleton = new VendingMachine();

    public static VendingMachine getInstance() {
        return singleton;
    }

    public int getStock(Product product) {
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
    public boolean isChangeAvailable(Product product) {
        if (balance - product.getPrice() <0) return false;
        else {
            this.balance -= product.getPrice();
            stockOut(product, 1);
            return true;
        }
    }

    public Product getDetail(int idx) {
        Product product = null;
        for(Product p : productMap.keySet()) {
            if(p.getIdx() == idx) product = p;
        }
        return product;
    }

    /**
     * 자판기에 재고를 등록한다
     * 만약 기존에 존재하는 물품이라면 수량만 변경한다
     * @param product 등록 상품
     * @param stock 등록 수량
     */
    public void stockUp(Product product, int stock) {
        // 재고 수량 확인 (기존 + 추가)
        stock += getStock(product);

        // 추가 가능한 재고일 때, 자판기에 물품 등록
        if (stock <=0 || stock > STOCK_MAX) throw new StockOutOfBoundsException(stock);
        productMap.put(product, stock);
    }

    /**
     * 자판기에 물품 재고를 제거한다
     * @param product
     */
    public void stockOut(Product product) {
        productMap.remove(product);
    }

    /**
     * 수량을 함께 적으면 수량만큼만 상품을 제거한다
     * 만약 남은 수량이 없다면 상품을 제거한다
     */
    public void stockOut(Product product, int count) {
        int stock = getStock(product);
        stock -= count;
        if (stock < 0)
            throw new LackOfStockException();
        else if(stock==0)
            productMap.remove(product);
        else
            productMap.put(product, stock-1);
    }


    /**
     * 충전된 돈을 반환한다
     */
    public int refundChange() {
        int refund = this.balance;
        this.balance = 0;
        return refund;
    }

    public Iterator productIterator() {
        return productMap.keySet().iterator();
    }

}
