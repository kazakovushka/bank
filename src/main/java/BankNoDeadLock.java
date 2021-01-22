import logic.BankTransaction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankNoDeadLock {
    public static void main(String[] args)  {
        Executors.newSingleThreadExecutor().submit(new Bank(BankTransaction.Mode.SYNC_BY_OBJ_NO_DEADLOCK));
    }
}
