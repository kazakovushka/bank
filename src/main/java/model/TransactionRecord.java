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
}
