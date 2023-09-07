package academy.prog;

import java.util.ArrayList;
import java.util.List;

public class JsonMessages {
    private final List<Message> list = new ArrayList<>();

    public JsonMessages(List<Message> sourceList, int fromIndex, String userName) {
        for (int i = fromIndex; i < sourceList.size(); i++) {
            Message message = sourceList.get(i);
            list.add(message);
        }
    }
}

//
//        if (message.getTo().equals("SERVER") && message.getFrom().equals(userName)){
//        message = MessageProcessing.createMessage(message, userName);
//        if (!list.contains(message)) list.add(message);
//        }else if (message.getFrom().equals(userName) || message.getTo().equals(userName) || message.getTo().equals("All")){
//        if (!list.contains(message)) list.add(message);