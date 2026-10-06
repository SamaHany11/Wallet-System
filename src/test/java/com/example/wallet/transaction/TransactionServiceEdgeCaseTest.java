package com.example.wallet.transaction;

import com.example.wallet.exception.DuplicateTransactionException;
import com.example.wallet.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceEdgeCaseTest {

    @Test
    void shouldRejectInvalidAmounts() {
        TransactionService service = new TransactionService();

        assertThrows(InvalidAmountException.class,
                () -> service.record("A-1", TransactionType.DEPOSIT, null));
        assertThrows(InvalidAmountException.class,
                () -> service.record("A-1", TransactionType.DEPOSIT, BigDecimal.ZERO));
        assertThrows(InvalidAmountException.class,
                () -> service.record("A-1", TransactionType.DEPOSIT, new BigDecimal("-1")));
    }

    @Test
    void shouldRejectTheSameTransactionTwice() {
        TransactionService service = new TransactionService();
        Transaction transaction = new Transaction("t-1", "A-1", TransactionType.DEPOSIT,
                new BigDecimal("10.00"), Instant.now(), TransactionStatus.COMPLETED);
        service.record(transaction);

        assertThrows(DuplicateTransactionException.class, () -> service.record(transaction));
        assertEquals(1, service.findByAccountId("A-1").size());
    }

    @Test
    void historyShouldKeepRecordingOrder() {
        TransactionService service = new TransactionService();
        service.record("A-1", TransactionType.DEPOSIT, new BigDecimal("1.00"));
        service.record("A-1", TransactionType.WITHDRAWAL, new BigDecimal("2.00"));

        List<Transaction> history = service.findByAccountId("A-1");

        assertEquals(TransactionType.DEPOSIT, history.get(0).getType());
        assertEquals(TransactionType.WITHDRAWAL, history.get(1).getType());
    }

    @Test
    void historyShouldNotBeChangeableFromOutside() {
        TransactionService service = new TransactionService();
        service.record("A-1", TransactionType.DEPOSIT, new BigDecimal("1.00"));

        List<Transaction> history = service.findByAccountId("A-1");

        assertThrows(UnsupportedOperationException.class, () -> history.clear());
    }
}