package sale_v2;

import error.InvalidInputException;
import error.InvalidProductIdException;
import error.LackOfStockException;
import error.StockOutOfBoundsException;
import product.Product;
import product.Stock;
import product.StockImpl;

import java.util.*;

public class VendingMachineV2 implements Machine{
    private int balance;
    private static final Machine singleton = new VendingMachineV2();        // 머신 변경
    private final List<Stock> stockList;


    public static Machine getInstance() {
        return singleton;
    }

    private VendingMachineV2() {
        this.stockList = new ArrayList<>();
        this.balance  = 0;

        // init setting
        Product[] sample = {
                Product.builder().idx(1).name("코카콜라").price(1200).build(),
                Product.builder().idx(2).name("사이다").price(1500).build(),
                Product.builder().idx(3).name("환타").price(500).build(),
                Product.builder().idx(4).name("다이제").price(200).build(),
        };
        for (int i=0; i<sample.length; i++){
            stockList.add(new StockImpl(sample[i], i+2));
        }
    }


    @Override
    public int getBalance() {
        return this.balance;
    }

    @Override
    public void chargeBalance(int money) {
        if(money > 0)
            this.balance += money;
        else throw new InvalidInputException();
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
        if(p == null) throw new InvalidProductIdException();
        if(balance >= p.getPrice()) {
            this.balance -= p.getPrice();
            return true;
        }
        return false;
    }

    @Override
    public int getStock(int id) {
        Stock stock = stockList.get(id);
        return stock.getStock();
    }

    @Override
    public Product getDetail(int id) {
        return stockList.get(id).getDetail();
    }

    @Override
    public void makeStock(Product product, int count) {
        if (count <=0 || count > STOCK_MAX) throw new StockOutOfBoundsException(count);
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
        if (stock.stockOut() < 0) {
//            stockList.remove(id);
            throw new LackOfStockException();
        }
    }

    @Override
    public void stockAllOut(int id) {
        stockList.remove(id);
    }

    @Override
    public Map<Integer, Stock> stockIterator() {
        Map<Integer, Stock> map = new HashMap<>();
        for(int i=0; i<stockList.size(); i++) {
            map.put(i, stockList.get(i));
        }
        return map;
    }
}
