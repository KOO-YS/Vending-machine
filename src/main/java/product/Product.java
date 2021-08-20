package product;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

@Getter
@Builder
public class Product {

    private @NonNull final int idx;
    private String name;        // 상품명
    private int price;          // 가격

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
//        return "[" + idx +
//                "] " + name +
//                " - " + price +
//                '원';
        return name+" ("+price+"원)";
    }
}
