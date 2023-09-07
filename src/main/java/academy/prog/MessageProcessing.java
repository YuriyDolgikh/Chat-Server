package academy.prog;

import java.util.Set;

public class MessageProcessing {

    static UserList userList = UserList.getInstance();


    public static Message mainProcessor(Message message){  // use inAddServlet to change incoming message before adding to msgList
        if (message.getFrom().equals("SERVER") && message.getText().contains("left the chat.")){
            processLeftTheChat(message.getText());
        }
        if (message.getTo().equals("SERVER")){
            if (message.getText().startsWith("--get")){
                message.setText(processGet(message));
            }
        }



        return message;
    }

    private static String processGet(Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<User> users = userList.getUsers();
        if (message.getText().startsWith("--get online")) {
            stringBuilder.append("Users online: [");
            for (User user : users) {
                if (user.isOnline()) {
                    stringBuilder.append("@" + user.getName() + ", ");
                }
            }
            stringBuilder.append("]");
        }
        if (message.getText().startsWith("--get user")){
            String askedUserName = message.getText().split(" ")[2].substring(1); // getting userName from text
            String usersStatus = "not registered";
            for (User user : users){
                if (user.getName().equals(askedUserName)){
                    if (userList.isUserOnline(askedUserName)){
                       usersStatus = "online";
                    }else {
                        usersStatus = "offline";
                    }
                }
            }
            stringBuilder.append("User ").append("@").append(askedUserName).append(" is ").append(usersStatus);
        }
        return stringBuilder.toString();
    }

    private static void processLeftTheChat(String sourceString){
        String userName = sourceString.split(" ")[1].substring(1);
        userList.setUserOffline(userName);
    }

}
