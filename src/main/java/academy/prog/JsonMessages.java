package academy.prog;

import java.util.ArrayList;
import java.util.List;

public class JsonMessages {
    private final List<Message> list = new ArrayList<>();
    private static final ChatRoomList roomList = ChatRoomList.getInstance();

    public JsonMessages(List<Message> sourceList, int fromIndex, String userName) {
        for (int i = fromIndex; i < sourceList.size(); i++) {
            Message message = sourceList.get(i);
            if (isMessageValid(message, userName)){
                list.add(message);
            }
        }
    }

    private static boolean isMessageValid(Message message, String userName){
        String from = message.getFrom();
        String to = message.getTo();
        if (to.equals("All") || to.equals("@" + userName) || from.equals(userName)){
            return true;
        }else if (to.startsWith("#") && roomList.isUserInRoomExist(to.substring(1), userName)){
            return true;
        }else {
            return false;
        }
    }

}
