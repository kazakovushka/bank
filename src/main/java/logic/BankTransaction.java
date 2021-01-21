package logic;

import lombok.experimental.UtilityClass;
import model.Account;

@UtilityClass
public class BankTransaction {

    public enum Mode {
        SIMPLE, SYNC_BY_CLASS, SYNC_BY_OBJ, SYNC_BY_OBJ_NO_DEADLOCK
    }

    public void transact(Account from, Account to, int payment, Mode mode) {
        switch (mode) {
            case SIMPLE:
                transact(from, to, payment);
                break;
            case SYNC_BY_CLASS:
                transactSyncByClass(from, to, payment);
                break;
            case SYNC_BY_OBJ:
                transactSync(from, to, payment);
                break;
            case SYNC_BY_OBJ_NO_DEADLOCK:
                transactCheckForDeadlock(from, to, payment);
        }
    }

    private void transact(Account from, Account to, int payment) {
        if (paymentIsPossible(from, payment)) {
            logInitialState(from, to, payment);
            doTransaction(from, to, payment);
            logEndState(from, to);
        } else {
            logBalanceError(from, payment);
        }
    }

    private void transactSync(Account from, Account to, int payment) {
        if (paymentIsPossible(from, payment)) {
            logInitialState(from, to, payment);
            doTransactionSync(from, to, payment);
            logEndState(from, to);
        } else {
            logBalanceError(from, payment);
        }
    }

    private synchronized void transactSyncByClass(Account from, Account to, int payment) {
        transact(from, to, payment);
    }


    public void transactCheckForDeadlock(Account from, Account to, int payment) {
        if (from.getId() <= to.getId()) {
            transactSync(from, to, payment);
        } else {
            transactSync(to, from, -1 * payment);
        }
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

    private void doTransactionSync(Account from, Account to, int payment) {
        synchronized (from) {
            synchronized (to) {
                from.setBalance(from.getBalance() - payment);
                to.setBalance(to.getBalance() + payment);
            }
        }
    }


}
