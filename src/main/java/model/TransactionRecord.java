package model;

import lombok.Data;

import java.util.UUID;

@Data
public class TransactionRecord {
    private UUID uuid;
    private long transactionCounter;
    private long timestamp;
    private Account from;
    private Account to;
    private int payment;
    private PaymentStatus status;

    public TransactionRecord(UUID uuid, long transactionCounter, long timestamp, Account from, Account to, int payment,
                             PaymentStatus status) {
        this.uuid = uuid;
        this.transactionCounter = transactionCounter;
        this.timestamp = timestamp;
        this.from = from;
        this.to = to;
        this.payment = payment;
        this.status = status;
    }
}
