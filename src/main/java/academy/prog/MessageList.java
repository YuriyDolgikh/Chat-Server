package academy.prog;

import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {
	private static final MessageList msgList = new MessageList();

    private final Gson gson;
	private final List<Message> list = new LinkedList<>();

	public static MessageList getInstance() {
		return msgList;
	}

	public synchronized List<Message> getList() {
		return list;
	}

	private MessageList() {
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}
	
	public synchronized void add(Message msg) {
		list.add(msg);
	}
	
	public synchronized String toJSON(int n, String userName) {
		if (n >= list.size()) return null;
		return gson.toJson(new JsonMessages(list, n, userName));
	}
}
