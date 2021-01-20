package logic;

import lombok.experimental.UtilityClass;
import model.Account;

@UtilityClass
public class BankTransaction {
    public void transact(Account from, Account to, int payment) {
        if (paymentIsPossible(from, payment)) {
            System.out.print(from.getName() + ":" + from.getBalance() + "---> " + to.getName() + ":" +
                    to.getBalance() + ". Перевод: "+payment);
            from.setBalance(from.getBalance() - payment);
            to.setBalance(to.getBalance() + payment);
            System.out.println("Результат:" + from.getName() + ":" + from.getBalance() + ", " + to.getName() + ":" +
                    to.getBalance() + ". " + Thread.currentThread().getName());
        } else System.out.println(from.getName() + " ,ваш баланс " + from.getBalance() +
                " требуется " + payment + ", перевод невозможен");

    }

    private boolean paymentIsPossible(Account from, int payment) {
        return from.getBalance() - payment >= 0;
    }
}
