import logic.BankTransactionService;

public class BankSyncByClass {
    public static void main(String[] args) {
        new Bank(BankTransactionService.Mode.SYNC_BY_CLASS).runBank();
    }
}
