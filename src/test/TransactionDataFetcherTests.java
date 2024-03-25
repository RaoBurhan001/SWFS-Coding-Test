import com.smallworld.data.TransactionJsonRepository;
import com.smallworld.data.TransactionRepository;
import com.smallworld.domain.TransactionDataFetcher;
import com.smallworld.domain.entities.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;

public class TransactionDataFetcherTests {

    private final TransactionRepository transactionRepository = Mockito.mock(TransactionJsonRepository.class);
    private final TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher(transactionRepository);

    @Test
    public void testGetTotalTransactionAmount_AnotherDataSet() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, BigDecimal.valueOf(50), "Muhammad Burhan", 20, "Saad Ali", 20, null, false, null));
        transactions.add(new Transaction(2, BigDecimal.valueOf(30), "Saad Ali", 20, "Muhammad Burhan", 20, null, false, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        BigDecimal totalAmount = transactionDataFetcher.getTotalTransactionAmount();

        Assert.assertEquals(0, totalAmount.compareTo(BigDecimal.valueOf(80)));
    }

    @Test
    public void testGetTotalSenderTransactionAmount_AllTransactionsFromOneSender_AnotherDataSet() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.valueOf(50), "Muhammad Burhan", 20, "Saad Ali", 20, null, false, null));
        transactions.add(new Transaction(14, BigDecimal.valueOf(30), "Muhammad Burhan", 20, "Saad Ali", 20, null, false, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        BigDecimal totalAmount = transactionDataFetcher.getTotalTransactionAmountSentBy("Muhammad Burhan");
        Assert.assertEquals(0, totalAmount.compareTo(BigDecimal.valueOf(80)));

        BigDecimal totalAmountNonPresentSender = transactionDataFetcher.getTotalTransactionAmountSentBy("Saad Ali");
        Assert.assertEquals(0, totalAmountNonPresentSender.compareTo(BigDecimal.ZERO));
    }


    @Test
    public void testGetTotalSenderTransactionAmount_DifferentSenders() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.ONE, "Muhammad Burhan", 20, "Saad Ali", 20, null, false, null));
        transactions.add(new Transaction(12, BigDecimal.TEN, "Saad Ali", 20, "Saad Ali", 20, null, false, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        BigDecimal totalAmount = transactionDataFetcher.getTotalTransactionAmountSentBy("Muhammad Burhan");
        Assert.assertEquals(0, totalAmount.compareTo(BigDecimal.ONE));

        BigDecimal totalAmountNonPresentSender = transactionDataFetcher.getTotalTransactionAmountSentBy("Saad Ali");
        Assert.assertEquals(0, totalAmountNonPresentSender.compareTo(BigDecimal.TEN));
    }

    @Test
    public void testGetMaxTransactionAmount_TransactionsPresent() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.ONE, "Muhammad Burhan", 20, "Saad Ali", 20, null, false, null));
        transactions.add(new Transaction(13, BigDecimal.TEN, "Saad Ali", 20, "Saad Ali", 20, null, false, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        BigDecimal maxTransactionAmount = transactionDataFetcher.getMaxTransactionAmount();
        Assert.assertEquals(0, maxTransactionAmount.compareTo(BigDecimal.TEN));
    }

    @Test
    public void testGetMaxTransactionAmount_NoTransactionsPresent() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        Assert.assertThrows(NoSuchElementException.class, transactionDataFetcher::getMaxTransactionAmount);
    }

    @Test
    public void testCountUniqueClients_DifferentClients() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.ONE, "Muhammad Burhan", 20, "Saad Ali", 20, null, false, null));
        transactions.add(new Transaction(14, BigDecimal.TEN, "Saad Ali", 20, "Saad Ali", 20, null, false, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        Long totalClients = transactionDataFetcher.countUniqueClients();
        Assert.assertEquals(Long.valueOf(2), totalClients);
    }

    @Test
    public void testCountUniqueClients_SameClient() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.ONE, "Muhammad Burhan", 20, "Muhammad Burhan", 20, null, false, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        Long totalClients = transactionDataFetcher.countUniqueClients();
        Assert.assertEquals(Long.valueOf(1), totalClients);
    }

    @Test
    public void testHasOpenComplianceIssues() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.ONE, "Muhammad Burhan", 20, "Ali", 20, 32, false, "Looks like fraud"));
        transactions.add(new Transaction(12, BigDecimal.ONE, "Saad", 20, "Burhan", 20, null, true, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        Assert.assertTrue(transactionDataFetcher.hasOpenComplianceIssues("Muhammad Burhan")); // Sender Case
        Assert.assertTrue(transactionDataFetcher.hasOpenComplianceIssues("Ali")); // Beneficiary Case
        Assert.assertFalse(transactionDataFetcher.hasOpenComplianceIssues("Saad")); // No open issue client
    }

    @Test
    public void testGetTransactionsByBeneficiaryName() {
        ArrayList<Transaction> burhansTransactions = new ArrayList<>();
        burhansTransactions.add(new Transaction(1, BigDecimal.ONE, "Burhan", 20, "Burhan", 20, null, true, null));

        ArrayList<Transaction> alisTransactions = new ArrayList<>();
        alisTransactions.add(new Transaction(13, BigDecimal.ONE, "Burhan", 20, "Ali", 20, null, true, null));

        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, BigDecimal.ONE, "Sender1", 20, null, 20, null, true, null));
        
        ArrayList<Transaction> sameTransactions = new ArrayList<>();
        transactions.add(new Transaction(1, BigDecimal.ONE, "Sender2", 20, "Ali", 20, null, true, null));
        transactions.add(new Transaction(2, BigDecimal.TEN, "Sender3", 20, "Ali", 20, null, true, null))

        ArrayList<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(burhansTransactions);
        allTransactions.addAll(alisTransactions);

        Mockito.when(transactionRepository.getAll()).thenReturn(allTransactions);
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);
        Mockito.when(transactionRepository.getAll()).thenReturn(sameTransactions);

        Map<String, List<Transaction>> result = transactionDataFetcher.getTransactionsByBeneficiaryName();

        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.containsKey("Burhan"));
        Assert.assertTrue(result.containsKey("Ali"));
        Assert.assertFalse(result.containsKey(null));
        Assert.assertTrue(result.containsKey("Ali"));

        Assert.assertEquals(1, result.get("Burhan").size());
        Assert.assertEquals(1, result.get("Ali").size());
        Assert.assertEquals(2, result.get("Ali").size());
        Assert.assertEquals(burhansTransactions.get(0), result.get("Burhan").get(0));
        Assert.assertEquals(alisTransactions.get(0), result.get("Ali").get(0));
        
    }

    @Test
    public void testGetOpenComplianceIssueIds() {
        Integer unresolvedIssueId = 32;
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.ONE, "Muhammad Burhan", 20, "Ali", 20, unresolvedIssueId, false, "Looks like fraud"));
        transactions.add(new Transaction(3, BigDecimal.TEN, "Muhammad Burhan", 20, "Ali", 20, 35, true, "Looks like fraud"));
        transactions.add(new Transaction(1, BigDecimal.ONE, "Burhan", 20, null, 20, null, true, null));
        transactions.add(new Transaction(4, BigDecimal.ONE, "Saad", 20, "Burhan", 20, null, true, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        Set<Integer> result = transactionDataFetcher.getUnsolvedIssueIds();

        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.contains(unresolvedIssueId));
        Assert.assertFalse(result.containsKey(null));
    }

    @Test
    public void testGetAllSolvedIssueMessages() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.ONE, "Muhammad Burhan", 20, "Ali", 20, 32, true, "Legit transaction"));
        transactions.add(new Transaction(3, BigDecimal.TEN, "Muhammad Burhan", 20, "Ali", 20, 35, false, "Looks like fraud"));
        transactions.add(new Transaction(4, BigDecimal.ONE, "Saad", 20, "Burhan", 20, null, true, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        List<String> issueSolvedMessages = transactionDataFetcher.getAllSolvedIssueMessages();

        Assert.assertEquals(1, issueSolvedMessages.size());
        Assert.assertEquals("Legit transaction", issueSolvedMessages.get(0));
    }

    @Test
    public void testGetTopSender() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.ONE, "Muhammad Burhan", 20, "Ali", 20, 32, true, "Legit transaction"));
        transactions.add(new Transaction(3, BigDecimal.TEN, "Muhammad Burhan", 20, "Ali", 20, 35, false, "Looks like fraud"));
        transactions.add(new Transaction(4, BigDecimal.TEN, "Saad", 20, "Burhan", 20, null, true, null));
        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        String topSender = transactionDataFetcher.getTopSender();

        Assert.assertTrue(topSender.equals("Muhammad Burhan"));
    }

    @Test
    public void testGetTop3TransactionsByAmount() {
        ArrayList<Transaction> expectedTopTransactions = new ArrayList<>();
        expectedTopTransactions.add(new Transaction(6, BigDecimal.valueOf(4000), "Ben", 20, "Burhan", 20, null, true, null));
        expectedTopTransactions.add(new Transaction(5, BigDecimal.valueOf(3000), "Alex", 20, "Burhan", 20, null, true, null));
        expectedTopTransactions.add(new Transaction(4, BigDecimal.valueOf(5), "Saad", 20, "Burhan", 20, null, true, null));


        ArrayList<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(1, BigDecimal.ONE, "Muhammad Burhan", 20, "Ali", 20, 32, true, "Legit transaction"));
        transactions.add(new Transaction(3, BigDecimal.valueOf(3), "Muhammad Burhan", 20, "Ali", 20, 35, false, "Looks like fraud"));
        transactions.addAll(expectedTopTransactions);

        Mockito.when(transactionRepository.getAll()).thenReturn(transactions);

        List<Transaction> topTransactions = transactionDataFetcher.getTop3TransactionsByAmount();

        Assert.assertEquals(3, topTransactions.size());
        for (Integer i = 0; i<3; i++) {
            Assert.assertEquals(expectedTopTransactions.get(i), topTransactions.get(i));
        }
    }
}
