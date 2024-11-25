package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import Model.Account;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message addMessage(Message message) {
        
        if (message.getMessage_text() != null && message.getMessage_text().length() <= 255 && isValidAccount(message.getPosted_by())) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessageById(int message_id, String message_text ) {

        Message existingMessage = messageDAO.getMessageById(message_id);
        if (existingMessage != null && message_text != null && message_text.length() <= 255) {
            return messageDAO.updateMessageById(message_id, message_text);
        }
        return null;
    }

    public List<Message> getAllMessagesByAccountId(int account_id) {
        return messageDAO.getAllMessagesByAccountId(account_id);
    }

    private boolean isValidAccount(int accountId) {
        List<Account> accounts = accountDAO.getAllAccounts();
        
        for (Account account : accounts) {
            if (account.getAccount_id() == accountId) {
                return true;
            }
        }
        return false;
    }
    
    
}
