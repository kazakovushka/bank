import logic.BankTransactionService;

public class BankNoDeadLock {
    public static void main(String[] args) {
        new Bank(BankTransactionService.Mode.SYNC_BY_OBJ_NO_DEADLOCK).runBank();
    }
}
