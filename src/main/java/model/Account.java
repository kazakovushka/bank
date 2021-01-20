package model;

import lombok.Data;

@Data
public class Account {
    int id;
    String name;
    int balance;

    public Account(int id, String name){
        this.id = id;
        this.name = name;
        this.balance = 10000;
    }
}
