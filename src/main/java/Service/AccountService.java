package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        if (
            account == null || 
            account.getUsername() == null ||
            account.getUsername().trim().isEmpty() ||
            account.getPassword() == null ||
            account.getPassword().length() < 4
            ) {
            System.out.println("Invalid account: name cannot be null and password must be at least four characters long");
            return null;
        }
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            System.out.println("Registration failed: username already exists.");
            return null;
        }
        
        return accountDAO.insertAccount(account);
    }
    public Account loginAccount (Account account) {
       
        if (account == null || 
        account.getUsername() == null || 
        account.getPassword() == null) {
        System.out.println("Invalid login: username and password must be provided.");
        return null;
        }

        Account targetAccount = accountDAO.getAccountByUsername(account.getUsername());

        if (
            targetAccount != null &&
            targetAccount.getPassword().equals(account.getPassword())) {
            return targetAccount;
        }

        System.out.println("Invalid login: incorrect username or password.");
        return null;
    }
    
    
}
