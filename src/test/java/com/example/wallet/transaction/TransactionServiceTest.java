package com.example.wallet.transaction;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionServiceTest {

    @Test
    void shouldRecordAndFindTransactionsByAccount() {
        TransactionService service = new TransactionService();

        service.record("A-1", TransactionType.DEPOSIT, new BigDecimal("100.00"));
        service.record("A-2", TransactionType.DEPOSIT, new BigDecimal("50.00"));

        assertEquals(1, service.findByAccountId("A-1").size());
        assertEquals(new BigDecimal("100.00"), service.findByAccountId("A-1").get(0).getAmount());
    }
}
