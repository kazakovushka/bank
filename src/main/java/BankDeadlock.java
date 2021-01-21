import logic.BankTransaction;

public class BankDeadlock {
    public static void main(String[] args)  {
        new Thread(new Bank(BankTransaction.Mode.SYNC_BY_OBJ)).start();
    }
}
