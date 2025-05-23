package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.h2.engine.User;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public DatabaseConduit(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }


    public void save(Transaction transaction){
        // 根据id查库确认id存在
        UserRecord sender = queryUser(transaction.getSenderId());
        UserRecord recipient = queryUser(transaction.getRecipientId());
        // 入库
        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount(), transaction.getIncentive());
        transactionRecordRepository.save(transactionRecord);

        sender.setBalance(sender.getBalance() - transaction.getAmount());
        save(sender);
        recipient.setBalance(recipient.getBalance() + transaction.getAmount() + transaction.getIncentive());
        save(recipient);
        // 查看余额
        System.out.println(sender.getName() + " -> " + recipient.getName() + " : " + transaction.getAmount());
        System.out.println("Sender: "+ sender.getName() + " Balance: " + sender.getBalance());
        System.out.println("Recipient: "+ recipient.getName() + " Balance: " + recipient.getBalance());
    }

    public UserRecord queryUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public boolean isValid(Transaction transaction) {
        UserRecord sender = queryUser(transaction.getSenderId());
        if (sender == null) {
            return false;
        }
        UserRecord recipient = queryUser(transaction.getRecipientId());
        if (recipient == null) {
            return false;
        }
        if (sender.getBalance() < transaction.getAmount()) {
            return false;
        }
        return true;
    }

    public float getUserBalance(Long userId) {
        // 正常查库
        UserRecord userRecord = queryUser(userId);
        if (userRecord == null) {
            return 0;
        } else {
            return userRecord.getBalance();
        }
    }
}
