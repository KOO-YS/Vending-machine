package sale_v2;

import product.Product;
import product.Stock;

import java.util.Map;

// 인스턴스 생성 메소드 동기화 방식 사용?
// https://velog.io/@kyle/%EC%9E%90%EB%B0%94-%EC%8B%B1%EA%B8%80%ED%86%A4-%ED%8C%A8%ED%84%B4-Singleton-Pattern
public interface Machine {
    int STOCK_MAX = 10;      // 자판기 재고 최대치

    // 충전
    int getBalance();           // 충전 금액 확인
    void chargeBalance(int money);      // 금액 충전
    int refundChange();         // 잔돈 반환

    // 구매
    boolean isEnoughMoney(int id);    // 물품 구매 가능한 자금인지 확인

    // 재고 관리
    int getStock(int id);      // 재고 확인
    Product getDetail(int id);     // 상품 확인

    void makeStock(Product product, int count);    // XXX
    void stockUp(int id, int add);         // 재고 충전
    void stockOut(int id);        // 재고 1개 제거
    void stockAllOut(int id);    // 재고 모두 제거 XXX

    // 확인
    Map<Integer, Stock> stockIterator();
}
