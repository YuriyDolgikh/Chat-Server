package academy.prog;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;

public class LoginServlet extends HttpServlet {
    UserList userList = UserList.getInstance();
    Set<User> users = userList.getUsers();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        InputStream is = req.getInputStream();
        Scanner scanner = new Scanner(is);

        String buffer = scanner.nextLine();

        User user = User.fromJSON(buffer);
        if (user != null){
            String login = user.getName();
            String password = user.getPassword();
            if (users.isEmpty() || !userList.isUserExist(login)){
                user.setOnline();
                userList.addUser(user);
                resp.setStatus(HttpServletResponse.SC_OK);
            }else if(!userList.getUser(login).getPassword().equals(password)){
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }else {
                userList.setUserOnline(login);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }
        else
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
