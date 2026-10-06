package com.example.wallet.transfer;

import com.example.wallet.account.Account;
import com.example.wallet.transaction.TransactionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferServiceTest {

    @Test
    void shouldTransferMoneyBetweenAccounts() {
        Account source = new Account("A-1", "Alice");
        Account destination = new Account("A-2", "Bob");
        source.deposit(new BigDecimal("500.00"));

        TransactionService transactions = new TransactionService();
        TransferService service = new TransferService(transactions);

        service.transfer(source, destination, new BigDecimal("125.00"));

        assertEquals(new BigDecimal("375.00"), source.getBalance());
        assertEquals(new BigDecimal("125.00"), destination.getBalance());
        assertEquals(1, transactions.findByAccountId("A-1").size());
        assertEquals(1, transactions.findByAccountId("A-2").size());
    }
}
