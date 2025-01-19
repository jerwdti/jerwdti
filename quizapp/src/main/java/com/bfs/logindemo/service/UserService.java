package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.UserDao;
import com.bfs.logindemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) { this.userDao = userDao; }

    public void createNewUser(User user) {
        userDao.createNewUser(user);
    }

    public Optional<User> validateLogin(String username, String password) {
        return userDao.getAllUsers().stream()
                .filter(a -> a.getEmail().equals(username)
                        && a.getPassword().equals(password))
                .findAny();
    }

    public List<User> getPaginatedUsers(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return userDao.getUsersWithPagination(offset, pageSize);
    }

    public int getTotalUserCount() {
        return userDao.getTotalUserCount();
    }

    public void toggleUserStatus(int userId) {
        userDao.toggleUserStatus(userId);
    }

}
