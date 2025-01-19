package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.UserDao;
import com.bfs.logindemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void createNewUser(User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("User email and password cannot be null.");
        }
        userDao.createNewUser(user);
    }

    public Optional<User> validateLogin(String username, String password) {
        return userDao.getUserByCredentials(username, password);
    }

    public List<User> getPaginatedUsers(int page, int pageSize) {
        if (page <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Page and page size must be positive integers.");
        }
        int offset = (page - 1) * pageSize;
        return userDao.getUsersWithPagination(offset, pageSize);
    }

    public int getTotalUserCount() {
        return userDao.getTotalUserCount();
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    public void toggleUserStatus(int userId) {
        userDao.toggleUserStatus(userId);
    }

    public User getUser(int userId) {
        return userDao.getUser(userId);
    }

    public void deleteUser(int userId) {
        userDao.deleteUserById(userId);
    }

    @Transactional
    public void updateUserStatus(int userId, boolean activate) {
        User user = userDao.getUser(userId);
        if (user != null) {
            user.setActive(activate);
            userDao.updateUser(user);
        }
    }
}