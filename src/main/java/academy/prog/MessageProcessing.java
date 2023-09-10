package academy.prog;

import java.util.Set;

public class MessageProcessing {

    static private UserList userList = UserList.getInstance();
    static private ChatRoomList roomList = ChatRoomList.getInstance();
    static String errCmdMsg = "Wrong command";

    public static synchronized Message mainProcessor(Message message){  // use in AddServlet to change incoming message before adding to msgList
        if (message.getFrom().equals("SERVER") && message.getText().contains("left the chat.")){
            processLeftTheChat(message.getText());
        }
        if (message.getTo().equals("SERVER")){
            if (message.getText().startsWith("--get")){
                message = processGet(message);
            }else if (message.getText().startsWith("--room")){      // !!!!!!!!!! что, если пришло --room ?
                message = processRoom(message);
            }
        }
        return message;
    }

    private static void processLeftTheChat(String sourceString){
        String userName = sourceString.split(" ")[1].substring(1);
        userList.setUserOffline(userName);
    }

    private static Message processGet(Message message) {
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
        }else if (message.getText().startsWith("--get user")){
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
        }else {
            stringBuilder.append(errCmdMsg);
        }
        return new Message("SERVER", "@" + message.getFrom(), stringBuilder.toString());
    }

    private static Message processRoom(Message message){
        Set<ChatRoom> rooms = roomList.getRooms();
        String[] partsOfText = message.getText().split(" ");
        String roomName = partsOfText[1].substring(1);
        String command = partsOfText[2];
        String memberOfRoomName;
        String newFrom = "SERVER";
        String newTo = "@" + message.getFrom();
        String newTextMessage = null;

        if (command.equals("set")){
            if (rooms.isEmpty() || !roomList.isRoomExist(roomName)){
                roomList.addRoom(new ChatRoom(roomName, message.getFrom()));
                newTextMessage = "Chat-room #" + roomName + " added";
            }else{
                newTextMessage = "Chat-room #" + roomName + " is already exist!";
            }
        }else if (command.equals("del")){
            if (!rooms.isEmpty() && roomList.isRoomExist(roomName) && roomList.isUserAdminInRoom(roomName,message.getFrom())){
                roomList.delRoom(roomName);
                newTextMessage = "Chat-room #" + roomName + " deleted";
            }else if (rooms.isEmpty() && !roomList.isRoomExist(roomName)){
                newTextMessage = "Chat-room #" + roomName + " is not exist!";
            }else if (!rooms.isEmpty() && roomList.isRoomExist(roomName) && !roomList.isUserAdminInRoom(roomName,message.getFrom())){
                newTextMessage = "You are not admin of room #" + roomName + " !";
            }
        }else if (command.equals("add")){
            memberOfRoomName = partsOfText[3].substring(1);
            if (roomList.isRoomExist(roomName)){
                if (roomList.isUserAdminInRoom(roomName, message.getFrom())){
                    if (!roomList.isUserInRoomExist(roomName, memberOfRoomName)){
                        roomList.addUserToRoom(roomName, memberOfRoomName);
                        newTextMessage = "User @" + memberOfRoomName + " join to chat-room #" + roomName;
                        newTo = "#" + roomName;
                    }else {
                        newTextMessage = "User @" + memberOfRoomName + " is already in chat-room #" + roomName;
                    }
                } else {
                    newTextMessage = "You are not admin of room #" + roomName + " !";
                }
            }else {
                newTextMessage = "Chat-room #" + roomName + " is not exist!";
            }
        }else if (command.equals("rem")){
            memberOfRoomName = partsOfText[3].substring(1);
            if (roomList.isRoomExist(roomName)){
                if (roomList.isUserAdminInRoom(roomName, message.getFrom())){
                    if (roomList.isUserInRoomExist(roomName, memberOfRoomName)){
                        roomList.removeUserFromRoom(roomName, memberOfRoomName);
                        newTextMessage = "User @" + memberOfRoomName + " removed from chat-room #" + roomName;
                        newTo = "#" + roomName;
                    }else {
                        newTextMessage = "User @" + memberOfRoomName + " is not in chat-room #" + roomName;
                    }
                }else {
                    newTextMessage = "You are not admin of room #" + roomName + " !";
                }
            }else {
                newTextMessage = "Chat-room #" + roomName + " is not exist!";
            }
        }else if (command.equals("list")){
            if (roomList.isRoomExist(roomName)){
                if (roomList.isUserInRoomExist(roomName, message.getFrom())){
                    newTextMessage = rooms.toString();
                }else {
                    newTextMessage = "You are not member of room #" + roomName + " !";
                }
            }else {
                newTextMessage = "Chat-room #" + roomName + " is not exist!";
            }
        }else {
            newTextMessage = errCmdMsg;
        }
        return new Message(newFrom, newTo, newTextMessage);
    }
}
