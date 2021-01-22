package logic;

import lombok.experimental.UtilityClass;
import model.Account;
import model.PaymentStatus;
import model.TransactionRecord;
import model.TransactionRecordStorage;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class BankTransactionService {

    private AtomicInteger counter;
    private TransactionRecordStorage storage;

    public enum Mode {
        SIMPLE, SYNC_BY_CLASS, SYNC_BY_OBJ, SYNC_BY_OBJ_NO_DEADLOCK
    }

    public BankTransactionService(TransactionRecordStorage storage) {
        this.storage = storage;
        this.counter = new AtomicInteger(0);
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
            store(from, to, payment, storage, PaymentStatus.SUCCESS);
            logEndState(from, to);
        } else {
            logBalanceError(from, payment);
            store(from, to, payment, storage, PaymentStatus.FAILED);
        }
    }

    private void store(Account from, Account to, int payment, TransactionRecordStorage storage,
                       PaymentStatus paymentStatus) {
        storage.storeTransactionRecord(new TransactionRecord(UUID.randomUUID(), counter.incrementAndGet(),
                Instant.now().toEpochMilli(), from, to, payment, PaymentStatus.SUCCESS));
    }

    private void transactSync(Account from, Account to, int payment) {
        synchronized (from) {
            synchronized (to) {
                transact(from, to, payment);
            }
        }
    }

    private synchronized void transactSyncByClass(Account from, Account to, int payment) {
        transact(from, to, payment);
    }


    private void transactCheckForDeadlock(Account from, Account to, int payment) {
        if (from.getId() <= to.getId()) {
            synchronized (from) {
                synchronized (to) {
                    transact(from, to, payment);
                }
            }
        } else {
            synchronized (to) {
                synchronized (from) {
                    transact(from, to, payment);
                }
            }
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
                to.getBalance() + ". " + Thread.currentThread().getName() + " " + Instant.now().toEpochMilli());
    }

    private boolean paymentIsPossible(Account from, int payment) {
        return from.getBalance() - payment >= 0;
    }

    private void doTransaction(Account from, Account to, int payment) {
        from.setBalance(from.getBalance() - payment);
        to.setBalance(to.getBalance() + payment);
    }


}
