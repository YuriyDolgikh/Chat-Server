package academy.prog;

import java.util.HashSet;
import java.util.Set;

public class ChatRoomList {

    private static final ChatRoomList chatRoomList = new ChatRoomList();

    private static final Set<ChatRoom> rooms = new HashSet<>();

    public static ChatRoomList getInstance(){
        return chatRoomList;
    }

    public synchronized void addRoom(ChatRoom chatRoom){
        rooms.add(chatRoom);
    }

    public synchronized void delRoom(String roomName){
        for (ChatRoom room : rooms){
            if (room.getName().equals(roomName)){
                rooms.remove(room);
                return;
            }
        }
    }

    public Set<ChatRoom> getRooms(){
        return rooms;
    }

    public boolean isRoomExist(String roomName){
        for (ChatRoom room : rooms){
            if (room.getName().equals(roomName)){
                return true;
            }
        }
        return false;
    }

    public String getAdminName(String roomName){
        for (ChatRoom room : rooms){
            if (room.getName().equals(roomName)){
                return room.getAdminName();
            }
        }
        return null;  // admin is not exist (strange!!!!!!!!!!)
    }

    public boolean isUserAdminInRoom(String roomName, String userName){
        for (ChatRoom room : rooms){
            if (room.getName().equals(roomName)){
                return room.getAdminName().equals(userName);
            }
        }
        return false;
    }

    public boolean isUserInRoomExist(String roomName, String userName){
        for (ChatRoom room : rooms){
            if (room.getName().equals(roomName)){
                return room.getMembers().contains(userName);
            }
        }
        return false;
    }

    public String getMembers(String roomName){
        for (ChatRoom room : rooms){
            if (room.getName().equals(roomName)){
                return room.getMembers().toString();
            }
        }
        return null;  // members are not exist (strange!!!!!!!!!! admin is always exist)
    }

    public void addUserToRoom(String roomName, String userName){
        for (ChatRoom room : rooms){
            if (room.getName().equals(roomName)){
                ChatRoom newRoom = new ChatRoom(roomName, getAdminName(roomName), room.getMembers());
                newRoom.addMember(userName);
                rooms.remove(room);
                rooms.add(newRoom);
            }
        }
    }

    public void removeUserFromRoom(String roomName, String userName){
        for (ChatRoom room : rooms){
            if (room.getName().equals(roomName)){
                ChatRoom newRoom = new ChatRoom(roomName, getAdminName(roomName), room.getMembers());
                newRoom.removeMember(userName);
                rooms.remove(room);
                rooms.add(newRoom);
            }
        }
    }

}
