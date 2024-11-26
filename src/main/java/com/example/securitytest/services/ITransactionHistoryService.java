package com.example.securitytest.services;

import com.example.securitytest.dtos.TransactionHistoryPair;
import org.springframework.stereotype.Service;

@Service
public interface ITransactionHistoryService {
    public TransactionHistoryPair saveTransactionHistory(String sendAccount, String receiveAccount, String amountTransaction) throws Exception;
}
