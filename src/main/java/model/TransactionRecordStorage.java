package model;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * хранилище хранит упорядоченные по времени записи о транзакциях
 */
public class TransactionRecordStorage {
    private ConcurrentSkipListSet<TransactionRecord> storage;

    public TransactionRecordStorage() {
        storage = new ConcurrentSkipListSet<>(Comparator.comparingLong(TransactionRecord::getTimestamp));
    }

    public void storeTransactionRecord(TransactionRecord record) {
        storage.add(record);
    }
}
