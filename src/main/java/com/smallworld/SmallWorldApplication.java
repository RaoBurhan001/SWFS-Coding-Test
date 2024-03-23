package com.smallworld;

import com.smallworld.config.Config;
import com.smallworld.data.TransactionJsonRepository;
import com.smallworld.domain.TransactionDataFetcher;
import com.smallworld.domain.entities.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SmallWorldApplication {

    private static void printApplicationIntroduction(String transactionsSource) {
        System.out.println("--------SWFS Transactions Fetcher Application--------");
        System.out.println("-----------------------------------------------------------");
        System.out.println("Transactions : " + transactionsSource);
        System.out.println("-----------------------------------------------------------");
    }

    private static void printApplicationExecution(TransactionDataFetcher transactionDataFetcher) {
        System.out.println("Total Sum of Transactions: " + transactionDataFetcher.getTotalTransactionAmount());
        System.out.println("-----------------------------------------------------------");
        System.out.println("Total Amount Sent by \"Ben Younger\": " + transactionDataFetcher.getTotalTransactionAmountSentBy("Ben Younger"));
        System.out.println("-----------------------------------------------------------");
        System.out.println("Maximum Amount: " + transactionDataFetcher.getMaxTransactionAmount());
        System.out.println("-----------------------------------------------------------");
        System.out.println("Unique Clients: " + transactionDataFetcher.countUniqueClients());
        System.out.println("-----------------------------------------------------------");
        System.out.println("Does \"Grace Burgess\" have transactions with open compliance issues? " + transactionDataFetcher.hasOpenComplianceIssues("Grace Burgess"));
        System.out.println("-----------------------------------------------------------");
        printTransactionsByBeneficiaries(transactionDataFetcher);
        System.out.println("Issues UnResolved: " + String.join(",", transactionDataFetcher.getUnsolvedIssueIds().stream().map(Object::toString).toList()));
        System.out.println("-----------------------------------------------------------");
        printIssueSolvedMessages(transactionDataFetcher);
        System.out.println("-----------------------------------------------------------");
        printTop3Transactions(transactionDataFetcher);
        System.out.println("-----------------------------------------------------------");
        System.out.println("Sender with highest total sent amount: " + transactionDataFetcher.getTopSender());
    }

    private static void printTransactionsByBeneficiaries(TransactionDataFetcher transactionDataFetcher) {
        Map<String, List<Transaction>> allTransactions = transactionDataFetcher.getTransactionsByBeneficiaryName();
        System.out.println("All Beneficiaries Transactions: ");
        for (Map.Entry<String, List<Transaction>> entry : allTransactions.entrySet()) {
            System.out.println("Transactions Carried By " + entry.getKey());

            for(Transaction transaction : entry.getValue()) {
                System.out.println(transaction);

            }
            System.out.println("-----------------------------------------------------------");
        }

    }

    private static void printIssueSolvedMessages(TransactionDataFetcher transactionDataFetcher) {
        System.out.println("Solved issue messages: ");
        List<String> solvedIssueMessages = transactionDataFetcher.getAllSolvedIssueMessages();
        System.out.println("-----------------------------------------------------------");
        for (String message: solvedIssueMessages) {
            System.out.println(message);
        }
        System.out.println("-----------------------------------------------------------");
    }

    private static void printTop3Transactions(TransactionDataFetcher transactionDataFetcher) {
        System.out.println("Top 3 transactions by amount: ");
        List<Transaction> top3Transactions = transactionDataFetcher.getTop3TransactionsByAmount();
        System.out.println("-----------------------------------------------------------");
        for (Transaction transaction : top3Transactions) {
            System.out.println("Amount: " + transaction.getAmount() + ", Transaction: " + transaction);
        }
        System.out.println("-----------------------------------------------------------");
    }

    public static void main(String[] args) throws Exception {
        Properties properties = Config.loadConfig();
        String transactionsSource = (String) properties.get("smallworld.transactions-source");

        TransactionJsonRepository transactionJsonRepository = new TransactionJsonRepository(transactionsSource);
        TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher(transactionJsonRepository);

        printApplicationIntroduction(transactionsSource);
        printApplicationExecution(transactionDataFetcher);
    }
}
