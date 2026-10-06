package com.example.wallet;

import com.example.wallet.account.Account;
import com.example.wallet.account.AccountService;
import com.example.wallet.transaction.Transaction;
import com.example.wallet.transaction.TransactionService;
import com.example.wallet.transfer.TransferService;

import java.math.BigDecimal;

/**
 * Deterministic acceptance runner for the wallet challenge.
 * It validates business state and values; a lack of exceptions is never treated as success.
 */
public final class WalletApplication {
    private static int passed;
    private static int failed;

    private WalletApplication() {}

    public static void main(String[] args) {
        System.out.println("=== Digital Wallet Java SWE Challenge Runner ===");
        run("Create accounts with zero balance", WalletApplication::createAccountsCase);
        run("Deposit increases balance", WalletApplication::depositCase);
        run("Transfer updates both balances", WalletApplication::transferCase);
        run("Transfer records transaction for both accounts", WalletApplication::transactionHistoryCase);
        run("Withdraw decreases balance", WalletApplication::withdrawCase);

        System.out.println("----------------------------------------");
        System.out.printf("Result: %d/%d passed%n", passed, passed + failed);
        System.out.println("Status: " + (failed == 0 ? "PASSED" : "FAILED"));
        if (failed > 0) System.exit(1);
    }

    private static boolean createAccountsCase() {
        AccountService accounts = new AccountService();
        Account alice = accounts.createAccount("A-100", "Alice");
        Account bob = accounts.createAccount("A-200", "Bob");
        return alice.getBalance().compareTo(BigDecimal.ZERO) == 0
                && bob.getBalance().compareTo(BigDecimal.ZERO) == 0;
    }

    private static boolean depositCase() {
        AccountService accounts = new AccountService();
        Account alice = accounts.createAccount("A-100", "Alice");
        alice.deposit(money("1000.00"));
        return alice.getBalance().compareTo(money("1000.00")) == 0;
    }

    private static boolean transferCase() {
        AccountService accounts = new AccountService();
        TransactionService transactions = new TransactionService();
        TransferService transfers = new TransferService(transactions);
        Account alice = accounts.createAccount("A-100", "Alice");
        Account bob = accounts.createAccount("A-200", "Bob");
        alice.deposit(money("1000.00"));

        transfers.transfer(alice, bob, money("300.00"));

        return alice.getBalance().compareTo(money("700.00")) == 0
                && bob.getBalance().compareTo(money("300.00")) == 0;
    }

    private static boolean transactionHistoryCase() {
        AccountService accounts = new AccountService();
        TransactionService transactions = new TransactionService();
        TransferService transfers = new TransferService(transactions);
        Account alice = accounts.createAccount("A-100", "Alice");
        Account bob = accounts.createAccount("A-200", "Bob");
        alice.deposit(money("1000.00"));

        transfers.transfer(alice, bob, money("300.00"));

        var aliceTransactions = transactions.findByAccountId(alice.getId());
        var bobTransactions = transactions.findByAccountId(bob.getId());
        return aliceTransactions.size() == 1
                && bobTransactions.size() == 1
                && aliceTransactions.get(0).getAmount().compareTo(money("300.00")) == 0
                && bobTransactions.get(0).getAmount().compareTo(money("300.00")) == 0;
    }

    private static boolean withdrawCase() {
        AccountService accounts = new AccountService();
        Account alice = accounts.createAccount("A-100", "Alice");
        alice.deposit(money("1000.00"));
        alice.withdraw(money("250.00"));
        return alice.getBalance().compareTo(money("750.00")) == 0;
    }

    private static BigDecimal money(String value) {
        return new BigDecimal(value);
    }

    private static void run(String name, Case scenario) {
        try {
            boolean result = scenario.execute();
            if (result) {
                passed++;
                System.out.println("[PASS] " + name);
            } else {
                failed++;
                System.out.println("[FAIL] " + name + " - expected state/value was not reached");
            }
        } catch (RuntimeException ex) {
            failed++;
            System.out.println("[FAIL] " + name + " - execution error: " + ex.getMessage());
        }
    }

    @FunctionalInterface
    private interface Case { boolean execute(); }
}
