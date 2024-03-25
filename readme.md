# SWFS coding test!

### Problem Statement

### Objective:
The objective of this coding test is to implement methods in the `TransactionDataFetcher` component to provide insights into the transactions of a simplified money transfer system.

### Dataset:
The dataset for transactions is stored in a JSON file named `transactions.json`, which contains information about each transaction. Each transaction entry includes:
- Unique transaction identifier (`mtn`)
- Transaction amount
- Sender information: Full name and age
- Beneficiary information: Full name and age
- Issue information: Issue identifier, whether the issue is solved, and the issue message

### Approach:
- Follow a layered architecture consisting of Presentation, Domain, and Data layers.
- The Presentation Layer displays computed results in the console.
- The Domain Layer contains core logic and entities related to transactions.
- The Data Layer loads transaction data from `transactions.json` and provides access methods.

### Architecture:
- Presentation Layer: Main class displays results, interacts with Domain Layer.
- Domain Layer: Core logic and entities (e.g., `TransactionDataFetcher`, `Transaction`).
- Data Layer: Loads transaction data, provides access methods.

### Implementation:
- Presentation Layer: Contains a main class to display computation results.
- Domain Layer: Implements `TransactionDataFetcher` and `Transaction`.
- Data Layer: Loads transaction data from `transactions.json`, provides access methods.

This architecture facilitates modularity, scalability, and maintainability of the codebase.

### Testing:
Testing of domain layer is also included.
