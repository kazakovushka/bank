package model;

import lombok.Data;

@Data
public class Account {
    int id;
    int balance;

    public Account(int id){
        this.id = id;
        this.balance = 1000;
    }
}
