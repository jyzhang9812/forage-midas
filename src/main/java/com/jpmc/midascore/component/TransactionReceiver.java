package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionReceiver {
    private DatabaseConduit databaseConduit;
    private TransactionHandler transactionHandler;

    public TransactionReceiver(DatabaseConduit databaseConduit,TransactionHandler transactionHandler) {
        this.databaseConduit = databaseConduit;
        this.transactionHandler = transactionHandler;
    }

    @KafkaListener(topics = "${general.kafka-topic}")
    public void receive(Transaction transaction) {
        // 1.接收数据 ok
//        System.out.println(transaction.getAmount());
//        databaseConduit.save(transaction);
        // 处理交易数据
        transactionHandler.handleTransaction(transaction);
    }
}
