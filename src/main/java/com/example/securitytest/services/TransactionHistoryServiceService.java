package com.example.securitytest.services;

import com.example.securitytest.dtos.TransactionHistoryPair;
import com.example.securitytest.entities.TransactionHistory;
import com.example.securitytest.repositories.TransactionHistoryRepository;
import com.example.securitytest.utils.AESUtil;
import com.example.securitytest.utils.RSAUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class TransactionHistoryServiceService implements ITransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final AESUtil aesUtil;

    @Override
    public TransactionHistoryPair saveTransactionHistory(String encryptSendAccount, String encryptReceiveAccount, String encryptInDebt) throws Exception {
        String decryptSendAccount = RSAUtil.decryptRSA(encryptSendAccount);
        String decryptReceiveAccount = RSAUtil.decryptRSA(encryptReceiveAccount);
        String decryptInDebt = RSAUtil.decryptRSA(encryptInDebt);
        BigDecimal decodedInDebt = new BigDecimal(decryptInDebt);
        String transactionId = UUID.randomUUID().toString();
        LocalDateTime transactionTime = LocalDateTime.now();
        TransactionHistory transactionSendAccount = TransactionHistory.builder()
                .transactionId(transactionId)
                .account(aesUtil.convertToDatabaseColumn(decryptSendAccount))
                .inDebt(decodedInDebt)
                .have(false)
                .transactionTime(transactionTime)
                .build();
        transactionHistoryRepository.save(transactionSendAccount);

        TransactionHistory transactionReceiveAccount = TransactionHistory.builder()
                .transactionId(transactionId)
                .account(aesUtil.convertToDatabaseColumn(decryptReceiveAccount))
                .inDebt(decodedInDebt)
                .have(true)
                .transactionTime(transactionTime)
                .build();
        transactionHistoryRepository.save(transactionReceiveAccount);

        return new TransactionHistoryPair(transactionSendAccount,transactionReceiveAccount);
    }
}
