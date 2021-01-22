import logic.BankTransactionService;
import lombok.SneakyThrows;
import model.Account;
import model.TransactionRecordStorage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;

public class Bank {
    BankTransactionService.Mode mode;

    public Bank(BankTransactionService.Mode mode) {
        this.mode = mode;
    }

    public static void getSum(List<Account> accounts) {
        System.out.println("total sum =" + accounts.stream().map(Account::getBalance)
                .reduce(Integer::sum).get());
    }


    public void runBank() {
        int threadCount = 10;
        int transactionNumber = 100000;

        List<Account> accounts = List.of(new Account(0, "Вася"),
                new Account(1, "Жора"), new Account(2, "Лена"));
        getSum(accounts);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(transactionNumber);

        TransactionRecordStorage storage = new TransactionRecordStorage();
        BankTransactionService bankTransactionService = new BankTransactionService(storage);

        for (int i = 0; i < transactionNumber; i++) {
            //executor.submit(new TransactRunner(latch, accounts, mode));
            executor.execute(() -> {
                int accountFromNumber = ThreadLocalRandom.current().nextInt(0, accounts.size());
                int accountToNumber = ThreadLocalRandom.current().nextInt(0, accounts.size());
                int payment = new Random().nextInt(10) * 10;
                bankTransactionService.transact(accounts.get(accountFromNumber),
                        accounts.get(accountToNumber), payment, mode);
                latch.countDown();
            });
        }


        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            accounts.forEach(System.out::println);
            getSum(accounts);
            executor.shutdown();
        }
    }
}
