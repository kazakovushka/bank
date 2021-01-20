package logic;

import lombok.experimental.UtilityClass;
import model.Account;

@UtilityClass
public class BankTransaction {


    public void transact(Account from, Account to, int payment) {
        if (paymentIsPossible(from, payment)) {
            logInitialState(from, to, payment);
            doTransaction(from, to, payment);
            logEndState(from, to);
        } else {
            logBalanceError(from, payment);
        }
    }

    public synchronized void transactSync(Account from, Account to, int payment) {
        transact(from, to, payment);
    }

    private void logInitialState(Account from, Account to, int payment) {
        System.out.print(from.getName() + ":" + from.getBalance() + "---> " + to.getName() + ":" +
                to.getBalance() + ". Перевод: " + payment);
    }

    private void logBalanceError(Account from, int payment) {
        System.out.println(from.getName() + " ,ваш баланс " + from.getBalance() +
                " требуется " + payment + ", перевод невозможен");
    }

    private void logEndState(Account from, Account to) {
        System.out.println(" Результат:" + from.getName() + ":" + from.getBalance() + ", " + to.getName() + ":" +
                to.getBalance() + ". " + Thread.currentThread().getName());
    }

    private boolean paymentIsPossible(Account from, int payment) {
        return from.getBalance() - payment >= 0;
    }

    private void doTransaction(Account from, Account to, int payment) {
        from.setBalance(from.getBalance() - payment);
        to.setBalance(to.getBalance() + payment);
    }

}
