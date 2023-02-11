package my.bank.users.clients.payment;

import my.bank.users.clients.account.model.AccountStatus;
import my.bank.users.clients.dao.ClientDAO;
import my.bank.users.clients.models.Client;
import my.bank.users.transaction.models.Transaction;
import my.bank.users.transaction.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClientSend {

    public static int send(Client payer, Transaction data){
        int result = 0;
        ClientDAO clientDAO = new ClientDAO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        //Payer data
        if(payer.getAccount().getAcc_status() == AccountStatus.ACTIVE) {
            Transaction payer_transaction = new Transaction(
                    payer.getId(),
                    data.getPayer(),
                    data.getPayee(),
                    data.getAmount(),
                    payer.getAccount().getAcc_number(),
                    formatter.format(LocalDate.now()),
                    TransactionType.WITHDRAW
            );

            BigDecimal transaction_amount = data.getAmount();
            BigDecimal payer_amount = payer.getAccount().getBalance();
            BigDecimal new_payer_amount = payer_amount.subtract(transaction_amount);

            if (new_payer_amount.compareTo(new BigDecimal("0")) >= 0) {
                payer.getAccount().setBalance(new_payer_amount);

                //Payee data
                Client payee = clientDAO.getForAccNumber(data.getAccount());
                if (payee != null && payee.getAccount().getAcc_status() == AccountStatus.ACTIVE){
                    Transaction payee_transaction = new Transaction(
                            payee.getId(),
                            data.getPayer(),
                            data.getPayee(),
                            data.getAmount(),
                            payee.getAccount().getAcc_number(),
                            formatter.format(LocalDate.now()),
                            TransactionType.DEPOSIT
                    );

                    BigDecimal payee_amount = payee.getAccount().getBalance();
                    BigDecimal new_payee_amount = payee_amount.add(transaction_amount);
                    payee.getAccount().setBalance(new_payee_amount);

                    result = clientDAO.send_money(payer, payee, payer_transaction, payee_transaction);
                }

            }
        }
        return result;
    }
}
