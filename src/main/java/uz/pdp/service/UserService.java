package uz.pdp.service;

import uz.pdp.baseAbstractions.BaseService;
import uz.pdp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserService implements BaseService<User> {
    public static ArrayList<User> users = new ArrayList<>();

    @Override
    public boolean add(User user) {
        if (!isDefined(user)) {
            users.add(user);
            return true;
        }
        return false;
    }

    private boolean isDefined(User user) {
        for (User u : users) {
            if (u != null && Objects.equals(u, user)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUsernameValid(String username) {
        return username.matches("^[a-z_]{4,13}$");
    }

    @Override
    public void update(UUID id, User user) {
        User old = getById(id);
        if (old != null) {
            old.setName(user.getName());
            old.setRole(user.getRole());
            old.setUsername(user.getUsername());
            old.setPassword(user.getPassword());
            old.updateTimestamp();
        }
    }

    @Override
    public void delete(UUID id) {
        User deletingUser = getById(id);
        if (deletingUser != null) {
            deletingUser.setActive(false);
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

    private User login(String username, String password) {
        User userByUsername = getUserByUsername(username);
        if (userByUsername != null && userByUsername.getPassword().equals(password)) {
            return userByUsername;
        }
        return null;
    }

    private User getUserByUsername(String username) {
        for (User user : users) {
            if (user != null && user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
