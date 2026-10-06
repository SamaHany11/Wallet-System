package com.example.wallet.transfer;

import com.example.wallet.account.Account;
import com.example.wallet.exception.InsufficientBalanceException;
import com.example.wallet.exception.InvalidAccountStateException;
import com.example.wallet.exception.InvalidTransferException;
import com.example.wallet.transaction.TransactionService;
import com.example.wallet.transaction.TransactionType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferServiceEdgeCaseTest {

    private final TransactionService transactions = new TransactionService();
    private final TransferService service = new TransferService(transactions);
    private final Account alice = new Account("A-1", "Alice");
    private final Account bob = new Account("A-2", "Bob");

    @Test
    void shouldRejectTransferToTheSameAccount() {
        alice.deposit(new BigDecimal("100.00"));

        assertThrows(InvalidTransferException.class,
                () -> service.transfer(alice, alice, new BigDecimal("10")));
        assertEquals(new BigDecimal("100.00"), alice.getBalance());
    }

    @Test
    void shouldRejectWhenSourceHasNotEnoughMoney() {
        alice.deposit(new BigDecimal("50.00"));

        assertThrows(InsufficientBalanceException.class,
                () -> service.transfer(alice, bob, new BigDecimal("50.01")));
        assertEquals(new BigDecimal("50.00"), alice.getBalance());
        assertTrue(transactions.findByAccountId("A-1").isEmpty());
    }

    @Test
    void shouldNotTakeMoneyWhenDestinationIsSuspended() {
        alice.deposit(new BigDecimal("100.00"));
        bob.suspend();

        assertThrows(InvalidAccountStateException.class,
                () -> service.transfer(alice, bob, new BigDecimal("10")));
        assertEquals(new BigDecimal("100.00"), alice.getBalance());
        assertTrue(transactions.findByAccountId("A-1").isEmpty());
    }

    @Test
    void shouldRecordTransferOutAndTransferIn() {
        alice.deposit(new BigDecimal("100.00"));

        service.transfer(alice, bob, new BigDecimal("40.00"));

        assertEquals(TransactionType.TRANSFER_OUT, transactions.findByAccountId("A-1").get(0).getType());
        assertEquals(TransactionType.TRANSFER_IN, transactions.findByAccountId("A-2").get(0).getType());
    }
}