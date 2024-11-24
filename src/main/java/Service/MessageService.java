package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import Model.Account;
import Service.AccountService;
import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        List<Account> list = accountDAO.getAllAccounts();
        List<Integer> account_ids = for (Account el : list) { 
            el -> el.getAccount_id
        }
        if (message.getMessage_text() != null && message.getMessage_text().length() <= 255 && account_ids.contains(message.getPosted_by())) {
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
        List<Account> list = accountDAO.getAllAccounts();
        List<Integer> account_ids = for (Account el : list) { 
            el -> el.getAccount_id
        }
        if (message_text != null && message_text.length() <= 255 && account_ids.contains(message_id) {
            return messageDAO.updateMessageById(message_id,message_text);
        }
        return null;
    }

    public List<Message> getAllMessagesByAccountId(int account_id) {
        return messageDAO.getAllMessagesByAccountId(account_id);
    }
    
    
}
