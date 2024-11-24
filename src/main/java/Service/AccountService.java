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
        if (account == null || account.getUsername() == null || account.getUsername().trim().isEmpty() || account.getPassword().length() < 4) {
            System.out.println("Invalid account: name cannot be null and password must be at least four characters long");
            return null;
        }
        
        return accountDAO.insertAccount(account);
    }
    public Account loginAccount (Account account) {
        List<Account> list = accountDAO.getAllAccounts();
        for (Account target : list )
            if (account.getUsername() == target.getUsername() && account.getPassword() == target.getPassword()) {
                return accountDAO.getAccount(account);
                
        }
        System.out.println("Invalid login");
        return null;
    }
    
    
}
