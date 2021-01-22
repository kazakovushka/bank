import logic.TransactRunner;
import logic.TransactSameTimeRunner;
import model.Account;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * здесь запуск всех транзакций одновременно с помощью countDownLatch
 */
public class BankStartSameTime {
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 10;
        int transactionNumber = 1000;


        List<Account> accounts = List.of(new Account(0, "Вася"),
                new Account(1, "Жора"), new Account(2, "Лена"));
        accounts.forEach(System.out::println);
        getSum(accounts);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        CountDownLatch allReadyToStart = new CountDownLatch(transactionNumber);
        CountDownLatch waitAllBlocker = new CountDownLatch(1);
        CountDownLatch allFinished = new CountDownLatch(transactionNumber);

        for (int i = 0; i < transactionNumber; i++) {
            executor.submit(new TransactSameTimeRunner(allReadyToStart, waitAllBlocker, allFinished, accounts));
        }

        allReadyToStart.await();
        waitAllBlocker.countDown();
        allFinished.await();
        executor.shutdown();
        accounts.forEach(System.out::println);
        getSum(accounts);
    }


    public static void getSum(List<Account> accounts) {
        System.out.println("total sum =" + accounts.stream().map(Account::getBalance)
                .reduce(Integer::sum).get());
    }

}
