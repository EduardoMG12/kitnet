package com.kitnet.kitnet.service;

import com.kitnet.kitnet.model.ContactMessage;
import com.kitnet.kitnet.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    public ContactMessage save(ContactMessage contactMessage) {
        return contactMessageRepository.save(contactMessage);
    }

    public List<ContactMessage> getAll() {
        return contactMessageRepository.findAll();
    }

    public ContactMessage getById(Long id) {
        return contactMessageRepository.findById(id).orElse(null);
    }
}
