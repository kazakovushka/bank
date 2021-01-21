package model;

public class AccountSync extends Account{
    public AccountSync(int id, String name) {
        super(id, name);
    }

    @Override
    public synchronized int getBalance() {
        return super.getBalance();
    }

    @Override
    public synchronized void setBalance(int balance) {
        super.setBalance(balance);
    }
}
