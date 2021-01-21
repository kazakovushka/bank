import logic.BankTransaction;

public class BankNoDeadLock {
    public static void main(String[] args)  {
        new Thread(new Bank(BankTransaction.Mode.SYNC_BY_OBJ_NO_DEADLOCK)).start();
    }
}
