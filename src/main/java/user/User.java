package user;

import sale.VendingMachine;

public abstract class User {

    // FIXME
    //  인터페이스로 변수 선언만 하되 초기화는 나중에 하도록 변경!
    protected final VendingMachine vendingMachine = VendingMachine.getInstance();

}
