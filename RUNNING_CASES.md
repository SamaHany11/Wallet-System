# Running Cases

## Acceptance runner

Run:

```bash
mvn test
mvn -q exec:java -Dexec.mainClass=com.example.wallet.WalletApplication
```

The runner validates returned values and resulting domain state. It does not treat the absence of an exception as success.

## Cases

| Case | Expected |
|---|---|
| Create accounts | Both balances are 0.00 |
| Deposit | Alice balance becomes 1000.00 |
| Transfer | Alice becomes 700.00 and Bob becomes 300.00 |
| Transaction history | One transfer transaction for each account |
| Withdraw | Alice becomes 750.00 after withdrawing 250.00 |
