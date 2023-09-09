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
