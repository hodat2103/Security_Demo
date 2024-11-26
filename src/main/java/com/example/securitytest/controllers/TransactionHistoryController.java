package com.example.securitytest.controllers;

import com.example.securitytest.dtos.TransactionHistoryPair;
import com.example.securitytest.dtos.request.TransactionHistoryRequest;
import com.example.securitytest.dtos.response.ResponseSuccess;
import com.example.securitytest.services.ITransactionHistoryService;
import com.example.securitytest.utils.RSAUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Controller", description = "APIs for managing transaction")
public class TransactionHistoryController {
    private final ITransactionHistoryService transactionHistoryService;

    @Operation(summary = "Save a transaction history", description = "Send a request via this API to save a transaction history concerning send account and receive account")
    @PostMapping("")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully transaction "),
            @ApiResponse(responseCode = "404", description = "URL not found while not return data or url failed"),
            @ApiResponse(responseCode = "400", description = "Client side error while invalid  input data such as validated"),
            @ApiResponse(responseCode = "500", description = "Internal server error while not connect with database")})
    public ResponseEntity<?> saveTransactionHistory(@Valid @RequestBody TransactionHistoryRequest request) throws Exception {

        String encryptSendAccount = RSAUtil.encryptRSA(request.getSendAccount());
        String encryptReceiveAccount = RSAUtil.encryptRSA(request.getReceiveAccount());
        String encryptAmountInDebt = RSAUtil.encryptRSA(request.getAmountInDebt().toPlainString());
        TransactionHistoryPair transactionHistoryPair = transactionHistoryService.saveTransactionHistory(encryptSendAccount,encryptReceiveAccount,encryptAmountInDebt);

        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK,"Giao dich thanh cong",transactionHistoryPair);

        return ResponseEntity.ok(responseSuccess);
    }

}
