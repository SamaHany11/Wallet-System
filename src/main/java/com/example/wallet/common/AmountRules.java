package com.example.wallet.common;

import com.example.wallet.exception.InvalidAmountException;

import java.math.BigDecimal;

public final class AmountRules {

    private AmountRules() {
    }

    public static void requirePositive(BigDecimal amount) {
        if (amount == null) {
            throw new InvalidAmountException("Amount must not be null");
        }
        if (amount.signum() <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }
    }
}