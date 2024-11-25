package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
            //Write SQL logic here
        try {
            String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"), 
                    rs.getInt("posted_by"), 
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return messages;
    }

    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int message_id = generatedKeys.getInt(1);
                    message.setMessage_id(message_id);
                    return message;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message getMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message deleteMessageById(int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String selectSql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setInt(1, messageId);
            ResultSet resultSet = selectStatement.executeQuery();
    
            if (resultSet.next()) {
                
                Message message = new Message(
                    resultSet.getInt("message_id"),
                    resultSet.getInt("posted_by"),
                    resultSet.getString("message_text"),
                    resultSet.getLong("time_posted_epoch")
                );
    
                String deleteSql = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                deleteStatement.setInt(1, messageId);
                deleteStatement.executeUpdate();
    
                return message;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }    

    public Message updateMessageById(int message_id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();
        Message existingMessage = getMessageById(message_id);

        if (existingMessage != null) {
            try {
                String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, message_text);
                preparedStatement.setInt(2, message_id);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 1) {
                    existingMessage.setMessage_text(message_text);
                    return existingMessage;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public List<Message> getAllMessagesByAccountId(int account_id) {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
    
}
