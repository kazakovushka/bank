import logic.BankTransactionService;

public class BankDeadlock {
    public static void main(String[] args)  {
        new Bank(BankTransactionService.Mode.SYNC_BY_OBJ).runBank();
    }
}
