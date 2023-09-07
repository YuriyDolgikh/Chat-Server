package academy.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {

    private String name;
    private String password;
    private boolean online;

    public User(String name, String password, boolean online) {
        this.name = name;
        this.password = password;
        this.online = online;
    }

    public static User fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, User.class);
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline() {
        this.online = true;
    }
    public void setOffline() {
        this.online = false;
    }
}
