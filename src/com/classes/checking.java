/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.classes;

import java.util.Date;

/**
 *
 * @author CSC
 */
public class checking extends account {
    public Integer AccountNo;
    public Double ServiceCharge;

    public Integer getAccountNo() {
        return AccountNo;
    }

    public void setAccountNo(Integer AccountNo) {
        this.AccountNo = AccountNo;
    }

    public Double getServiceCharge() {
        return ServiceCharge;
    }

    public void setServiceCharge(Double ServiceCharge) {
        this.ServiceCharge = ServiceCharge;
    }

    public String getDateOpened() {
        return DateOpened;
    }

    public void setDateOpened(String DateOpened) {
        this.DateOpened = DateOpened;
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
