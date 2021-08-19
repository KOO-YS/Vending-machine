package sale_v2;

import product.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MachineImpl implements Machine<Stock>{
    private int balance;
    private final List<Stock> stockList;

    private static final Machine singleton = new MachineImpl();

    public static Machine getInstance() {
        return singleton;
    }

    private MachineImpl() {
        // QUESTION : ArrayList를 사용했더라면?
        this.stockList = new ArrayList<>();
        this.balance  = 0;
    }


    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void chargeBalance(int money) {
        if(money > 0)
            this.balance += money;
        // TODO : money의 입력값이 잘못된 경우
    }

    @Override
    public int refundChange() {
        int refund = getBalance();
        this.balance = 0;
        return refund;
    }

    @Override
    public boolean isEnoughMoney(int id) {
        Product p = stockList.get(id).getDetail();
        if(balance >= p.getPrice()) return true;
        return false;
    }

    @Override
    public int getStock(int id) {
        Stock stock = stockList.get(id);
        return stock.getStock();
    }

    @Override
    public Stock getDetail(int id) {
        return stockList.get(id);
    }

    @Override
    public void makeStock(Product product, int count) {
        Stock stock = StockImpl.builder().product(product).count(count).build();
        stockList.add(stock);
    }

    @Override
    public void stockUp(int id, int add) {
        Stock stock = stockList.get(id);
        stock.stockUp(add);
    }

    @Override
    public void stockOut(int id) {
        Stock stock = stockList.get(id);
        if (stock.stockOut() == 0)
            stockList.remove(id);
    }

    @Override
    public void stockAllOut(int id) {
        stockList.remove(id);
    }

    @Override
    public Iterator<Stock> stockIterator() {
        return stockList.iterator();
    }
}
