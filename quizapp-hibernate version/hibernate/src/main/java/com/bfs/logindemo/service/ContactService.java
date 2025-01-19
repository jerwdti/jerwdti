package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.ContactDao;
import com.bfs.logindemo.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactDao contactDao;

    @Autowired
    public ContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public boolean sendMessageToAdmin(String subject, String email, String message) {
        try {
            Contact contact = new Contact();
            contact.setSubject(subject);
            contact.setEmail(email);
            contact.setDetail(message);

            contactDao.saveContact(contact);
            return true;
        } catch (Exception e) {
            e.printStackTrace();  // Log the error
            return false;
        }
    }

    public List<Contact> getAllMessages() {
        return contactDao.getAllContacts();
    }
}