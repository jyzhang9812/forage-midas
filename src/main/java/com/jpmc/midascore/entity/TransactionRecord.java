package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue()
    private long id;

    @ManyToOne
    private UserRecord sender;

    @ManyToOne
    private UserRecord recipient;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private float incentive;

    // 为什么需要两个构造函数
    // 无参构造函数
    //JPA 要求所有实体类（加了 @Entity 的类）必须有一个 public 或 protected 的无参构造函数。
    //Hibernate 在加载数据库数据时，会使用 反射 创建实例，然后再调用 setXXX() 方法给字段赋值。
    //不能省略，否则运行时会报错类似：
    //org.hibernate.InstantiationException: No default constructor for entity
    protected TransactionRecord() {
    }

    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, float incentive) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = incentive;
    }

    public UserRecord getsender() {
        return sender;
    }

    public void setsender(UserRecord sender) {
        this.sender = sender;
    }

    public UserRecord getrecipient() {
        return recipient;
    }

    public void setrecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getIncentive() {
        return incentive;
    }

    public void setIncentive(float incentive) {
        this.incentive = incentive;
    }

    @Override
    public String toString() {
        return "TransactionRecord {sender=" + sender + ", recipient=" + recipient + ", amount=" + amount +  "}";
    }
}
