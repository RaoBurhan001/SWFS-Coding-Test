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


# Welcome to our coding test!
Your solution to this coding test will be evaluated based on its:
 * Adherence to best coding practices
 * Correctness
 * Efficiency

Take your time to fully understand the problem and formulate a plan before starting to code, and don't hesitate to ask any questions if you have doubts.

# Objective

Since we are a money transfer company this test will revolve around a (very) simplified transaction model. Our aim is to implement the methods listed in `com.smallworld.TransactionDataFetcher`, a component that allows us to get some insight into the transactions our system has.

A battery of test transactions is stored in `transactions.json` that is going to be used as a datasource for all our data mapping needs.

Each entry in `transactions.json` consists of:
 * mtn: unique identifier of the transaction
 * amount
 * senderFullName, senderAge: sender information
 * beneficiaryFullName, beneficiaryAge: beneficiary information
 * issueId, issueSolved, issueMessage: issue information. Transactions can:
   * Contain no issues: in this case, issueId = null.
   * Contain a list of issues: in this case, the transaction information will be repeated in different entries in `transactions.json` changing the issue related information.

Each method to be implemented includes a brief description of what's expected of it.

The parameters and return types of each method can be modified to fit the model that contains the transaction information

Have fun!
