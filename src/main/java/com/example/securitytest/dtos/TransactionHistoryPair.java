package com.example.securitytest.dtos;

import com.example.securitytest.entities.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistoryPair {
    private TransactionHistory sendTransaction;
    private TransactionHistory receiveTransaction;
}
