package academy.prog;

import java.util.HashSet;
import java.util.Set;

public class UserList {
    private static final UserList userList = new UserList();

    private static final Set<User> users = new HashSet<>();

    public static UserList getInstance(){
        return userList;
    }

    public synchronized void addUser(User user){
        users.add(user);
    }

    public synchronized User getUser(String userName){
        for (User user : users){
            if (user.getName().equals(userName)){
                return user;
            }
        }
        return null;
    }

    public boolean isUserExist(String userName){
        for (User user : users){
            if (user.getName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    public boolean isUserOnline(String userName){
        return getUser(userName).isOnline();
    }

    public void setUserOffline(String userName){
        String psw = null;
        for (User user : users){
            if (user.getName().equals(userName)){
                psw = user.getPassword();
                users.remove(user);
                break;
            }
        }
        users.add(new User(userName, psw, false));
    }

    public void setUserOnline(String userName){
        String psw = null;
        for (User user : users){
            if (user.getName().equals(userName)){
                psw = user.getPassword();
                users.remove(user);
                break;
            }
        }
        users.add(new User(userName, psw, true));
    }

    public Set<User> getUsers() {
        return users;
    }
}
