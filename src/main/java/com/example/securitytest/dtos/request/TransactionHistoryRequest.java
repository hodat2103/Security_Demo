package com.example.securitytest.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryRequest {
    @NotBlank(message = "SendAccount must not be blank")
    @JsonProperty("send_account")
    private String sendAccount;

    @NotBlank(message = "ReceiveAccount must not be blank")
    @JsonProperty("receive_account")
    private String receiveAccount;

    @NotNull(message = "In debt must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @JsonProperty("amount_in_debt")
    private BigDecimal amountInDebt;
}
