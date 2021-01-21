package model;

import lombok.Data;

@Data
public class Account {
    private int id;
    private String name;
    private int balance;

    public Account(int id, String name){
        this.id = id;
        this.name = name;
        this.balance = 10000;
    }
}
