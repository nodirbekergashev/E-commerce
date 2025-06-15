package uz.pdp.service;

import uz.pdp.baseAbstractions.BaseService;
import uz.pdp.enums.UserRole;
import uz.pdp.model.User;
import uz.pdp.wrapper.UserListWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static uz.pdp.fileUtils.XmlUtil.readFromXmlFile;
import static uz.pdp.fileUtils.XmlUtil.writeToXmlFile;

public class UserService implements BaseService<User> {
    public static List<User> users = new ArrayList<>();
    private static final String pathName = "users.xml";

    static {
        users = readFromXmlFile(pathName, User.class);
    }

    public UserService() {
        if (users.isEmpty()) {
            User user = new User(); // SuperAdmin
            user.setUsername("admin");
            user.setPassword("123");
            user.setRole(UserRole.ADMIN
            );
        }
    }

    @Override
    public boolean add(User user) {
        if (!isUsed(user)) {
            users.add(user);
            saveUsersToFile();
            return true;
        }
        return false;
    }

    private boolean isUsed(User user) {
        for (User u : users) {
            if (u != null && Objects.equals(u.getUsername(), user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameValid(String username) {
        return username.matches("^[a-zA-Z0-9_]{4,13}$");
    }

    @Override
    public void update(UUID id, User user) {
        User old = getById(id);
        if (old != null) {
            old.setName(user.getName());
            old.setRole(user.getRole());
            old.setUsername(user.getUsername());
            old.setPassword(user.getPassword());
        }
    }

    @Override
    public void delete(UUID id) {
        User deletingUser = getById(id);
        if (deletingUser != null) {
            deletingUser.setActive(false);
            saveUsersToFile();
        }
    }

    @Override
    public List<User> showAll() {
        return users;
    }

    @Override
    public User getById(UUID id) {
        for (User user : users) {
            if (user != null && user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public User login(String username, String password) {
        User userByUsername = getUserByUsername(username);
        if (userByUsername != null && userByUsername.getPassword().equals(password)) {
            return userByUsername;
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user != null && user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void changeUserRole(String username, UserRole newRole) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setRole(newRole);
                saveUsersToFile();
                return;
            }
        }
    }

    private void saveUsersToFile() {
        writeToXmlFile(new File(pathName), new UserListWrapper(users));
    }

}
