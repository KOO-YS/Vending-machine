package sale;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Product {

    private int idx;
    private String name;        // 상품명
    private int price;          // 가격

    public Product(int idx) {
        this.idx = idx;
    }
    public Product(int idx, String name, int price) {
        this.idx = idx;
        this.name = name;
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return idx == product.idx;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idx);
    }

    @Override
    public String toString() {
        return "[" + idx +
                "] " + name +
                " - " + price +
                '원';
    }
}
