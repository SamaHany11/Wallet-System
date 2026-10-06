# Digital Wallet Challenge Requirements

## Goal
Complete the partially implemented wallet domain while preserving the business behavior described below.

## Functional requirements
1. Accounts start with zero balance and ACTIVE status.
2. Account IDs must be unique.
3. Deposits require a positive amount and increase the balance.
4. Withdrawals require a positive amount and sufficient funds.
5. Suspended or closed accounts must not perform monetary operations.
6. A transfer moves the exact amount from source to destination.
7. A transfer must not be allowed when source and destination are the same account.
8. A transfer requires a positive amount and sufficient source funds.
9. A successful transfer records a transfer-out transaction for the source and transfer-in transaction for the destination.
10. Transaction history must not expose mutable internal collections.
11. Monetary values must use `BigDecimal`, never `double` or `float`.
12. The runner and tests must pass when the implementation is complete.

## Engineering requirements
- Apply encapsulation and single responsibility.
- Keep account invariants in appropriate domain boundaries.
- Avoid putting every rule into one service class.
- Avoid duplicated validation logic.
- Use meaningful names and small methods.
- Avoid global/static mutable state.
- Create additional classes/methods/interfaces when useful.
- Do not modify the acceptance runner to make scenarios pass.
- Do not remove or weaken the supplied tests.
