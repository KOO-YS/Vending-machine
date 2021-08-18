package user;

import lombok.Builder;
import sale.VendingMachine;

public abstract class User {

    @Builder.Default
    protected final VendingMachine vendingMachine = VendingMachine.getInstance();

}
