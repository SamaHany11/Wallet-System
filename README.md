# Digital Wallet Java SWE Challenge

A deliberately incomplete real-life backend/domain challenge. The goal is not only to make the happy-path tests pass, but to demonstrate clean OOP and maintainable design.

## Run the application

```bash
mvn exec:java
```

The entry point is:

```text
com.example.wallet.WalletApplication
```

## Run tests

```bash
mvn test
```

The provided tests focus on happy paths. They are intended to describe the expected basic behavior while leaving design and edge cases to the developer.

## Candidate tasks

1. Complete missing logic in existing methods.
2. Implement stub methods.
3. Add new methods/classes when the requirements need them.
4. Keep all existing tests passing.
5. Add your own unit tests for edge cases.
6. Apply OOP and clean-code principles.

## Functional requirements

- Create accounts.
- Accounts start with a zero balance.
- Deposit money into an active account.
- Withdraw money when sufficient funds are available.
- Transfer money between accounts.
- Record transactions.
- Retrieve an account's transaction history.
- Reject invalid monetary amounts.
- Prevent operations on invalid account states.
- Prevent duplicate account IDs.
- Define sensible rules for closing accounts.
- Keep transaction history encapsulated.

## Design expectations

- Use `BigDecimal` for money.
- Avoid global/static mutable state.
- Keep responsibilities separated.
- Prefer domain invariants in the domain model where appropriate.
- Avoid duplicated business rules.
- Avoid giant service classes.
- Use meaningful names.
- Create new abstractions when they genuinely improve the design.
- Do not change the intent of the provided tests.

## Deliberate gaps

Some methods are incomplete. Some requirements do not have a pre-created method so that the candidate has to decide where the behavior belongs. The candidate should not assume that the existing class structure is the only possible design.

## Suggested hidden tests for reviewers

- Duplicate account ID.
- Null/zero/negative amounts.
- Insufficient balance.
- Suspended account.
- Closed account.
- Transfer to the same account.
- Transaction history isolation.
- Transaction ordering.
- Invalid account lookup.
- Duplicate transaction processing.
- Decimal precision.
