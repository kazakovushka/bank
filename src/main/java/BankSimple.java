import logic.BankTransactionService;

public class BankSimple {
    public static void main(String[] args) {
       new Bank(BankTransactionService.Mode.SIMPLE).runBank();
    }
}
