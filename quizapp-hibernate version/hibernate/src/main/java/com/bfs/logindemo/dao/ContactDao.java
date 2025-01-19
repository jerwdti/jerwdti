package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Contact;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ContactDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveContact(Contact contact) {
        entityManager.persist(contact);
    }

    @Transactional
    public List<Contact> getAllContacts() {
        // Create CriteriaBuilder instance
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Create CriteriaQuery for Contact entity
        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);

        // Define root for the query (FROM Contact)
        Root<Contact> rootEntry = cq.from(Contact.class);

        // Define SELECT query
        CriteriaQuery<Contact> allContactsQuery = cq.select(rootEntry);

        // Execute the query
        return entityManager.createQuery(allContactsQuery).getResultList();
    }
}