package model;

import java.util.concurrent.ConcurrentSkipListSet;

public class TransactionRecordStorage {
    private TransactionRecordStorage instance;
    private ConcurrentSkipListSet<TransactionRecord> storage;

    private TransactionRecordStorage(){};

    public TransactionRecordStorage getInstance(){
        if (instance==null){
            instance = new TransactionRecordStorage();
            storage = new ConcurrentSkipListSet<>()
        }
        return instance;
    }

    public void storeTransactionRecord(TransactionRecord record){

    }


}
