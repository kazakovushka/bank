import logic.BankTransaction;

public class BankSyncByClass {
    public static void main(String[] args)  {
        new Thread(new Bank(BankTransaction.Mode.SYNC_BY_CLASS)).start();
    }
}
