package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createNewUser(User user) {
        entityManager.persist(user);
    }


    public Optional<User> getUserByCredentials(String email, String password) {
        String hql = "FROM User u WHERE u.email = :email AND u.password = :password";
        try {
            User user = entityManager.createQuery(hql, User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<User> getUsersWithPagination(int offset, int limit) {
        String hql = "FROM User u ORDER BY u.id ASC";
        return entityManager.createQuery(hql, User.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public int getTotalUserCount() {
        String hql = "SELECT COUNT(u) FROM User u";
        return ((Long) entityManager.createQuery(hql).getSingleResult()).intValue();
    }

    @Transactional
    public void toggleUserStatus(int userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            user.setActive(!user.isActive());
            entityManager.merge(user);
        }
    }

    public User getUser(int userId) {
        User user = entityManager.find(User.class, userId);
        return user;
    }

    public List<User> getAllUsers() {
        String hql = "FROM User u";
        return entityManager.createQuery(hql, User.class).getResultList();
    }

    @Transactional
    public void deleteUserById(int userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Transactional
    public void updateUser(User user) {
        entityManager.merge(user);
    }
}