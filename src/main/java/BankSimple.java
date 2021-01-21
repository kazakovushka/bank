import logic.BankTransaction;

public class BankSimple {
    public static void main(String[] args)  {
       new Thread(new Bank(BankTransaction.Mode.SIMPLE)).start();
    }
}
