package my.bank.users.employees.payment;

import my.bank.users.clients.account.model.AccountStatus;
import my.bank.users.clients.dao.ClientDAO;
import my.bank.users.clients.models.Client;
import my.bank.users.transaction.models.Transaction;
import my.bank.users.transaction.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClientDeposit {

    public static int deposit(Transaction data){
        Client client = new ClientDAO().getForAccNumber(data.getAccount());
        if(client != null &&
            client.getAccount().getAcc_status() != AccountStatus.INACTIVE) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
            Transaction transaction = new Transaction(
                    client.getId(),
                    data.getPayer(),
                    data.getPayee(),
                    data.getAmount(),
                    data.getAccount(),
                    formatter.format(LocalDate.now()),
                    TransactionType.DEPOSIT
            );

            //set new client balance
            BigDecimal clientAmount = new BigDecimal(client.getAccount().getBalance() + "");
            BigDecimal transactionAmount = new BigDecimal(data.getAmount() + "");
            BigDecimal newAmount = clientAmount.add(transactionAmount);

            client.getAccount().setBalance(newAmount);
            return new ClientDAO().payment(transaction, client);
        }
        return 0;
    }
}
