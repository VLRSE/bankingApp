package com.classes;

import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CSC
 */
public class account {
    public String DateOpened;
    public Integer AccountNo;
    public Double Balance;
    Date date;

    public String getDateOpened() {
        return DateOpened;
    }

    public void setDateOpened(String DateOpened) {
        this.DateOpened = DateOpened;
    }

    public Integer getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(Integer AccountNo) {
        this.AccountNo = AccountNo;
    }

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double Balance) {
        this.Balance = Balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
}
