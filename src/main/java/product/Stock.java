package product;

public interface Stock {

    Stock makeStock(Product t, int count);     // 재고 등록
    void stockUp(int count);             // 재고 추가
    int stockOut();    // 재고 1개 제거
    void stockAllOut(Product t); // 재고 전체 제거
    
    int getStock();     // 수량 확인
    Product getDetail();      // 상품 확인
    void updateDetail(Product t);    // 상품 수정
}
