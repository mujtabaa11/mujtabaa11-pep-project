package Controller;

import Model.Message;
import Model.Account;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    ObjectMapper mapper;
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
        this.mapper = new ObjectMapper();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::userRegistrationHandler);
        app.post("/login", this::userLoginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::retrieveAllMessagesHandler);
        app.get("/messages/{message_id}", this::retrieveMessageByMessageIdHandler);
        app.get("/accounts/{account_id}/messages", this::retrieveAllMessagesForUserHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByMessageIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageTextHandler);
       

        return app;
    }

      private void userRegistrationHandler(Context ctx) throws JsonProcessingException {
        
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.registerAccount(account);

        if(newAccount != null){
            ctx.json(newAccount);
        }else{
            ctx.status(400).result("Invalid registeration. Please try again");
        }
    }

    private void userLoginHandler(Context ctx) throws JsonProcessingException {

        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.loginAccount(account);

        if (loggedInAccount != null) {
            ctx.json(loggedInAccount);
        } else {
            ctx.status(401).result("Invalid username or password.");
        }
    }

    private void retrieveAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException {

        Message message = mapper.readValue(ctx.body(), Message.class);
        Message createdMessage = messageService.addMessage(message);
        
        if(createdMessage !=null) {
            ctx.json(createdMessage);
        }else{
            ctx.status(400);
        }
    }

    private void retrieveMessageByMessageIdHandler(Context ctx) throws JsonProcessingException {
       
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message != null) {
            ctx.json(message);
        } else {
            ctx.result("");
        }
    }

    private void deleteMessageByMessageIdHandler(Context ctx) throws JsonProcessingException {
       
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(messageId);

        if (deletedMessage != null) {
            ctx.json(deletedMessage);
        } else {
            ctx.result("");
        }
    }

    private void updateMessageTextHandler(Context ctx) throws JsonProcessingException {
       
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
    
        if (message.message_text == null || message.message_text.trim().isEmpty()) {
            ctx.status(400).result("Message text cannot be empty.");
            return;
        }
    
        if (message.message_text.length() > 255) {
            ctx.status(400).result("Message text cannot exceed 255 characters.");
            return;
        }
    
        Message updatedMessage = messageService.updateMessageById(messageId, message.message_text);
    
        if (updatedMessage != null) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(404).result("Message not found or invalid data.");
        }
    }

    private void retrieveAllMessagesForUserHandler(Context ctx) throws JsonProcessingException {
        
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
        ctx.json(messages);
        
    }


}