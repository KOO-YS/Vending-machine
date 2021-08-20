package user;

import sale_v2.Machine;

public abstract class User {
    protected Machine vendingMachine;
    public void setVendingMachine(Machine machine) {
        this.vendingMachine = machine;
//        System.out.println(vendingMachine.getClass().getSimpleName()+" 클래스의 자판기 구현");
    }
}
