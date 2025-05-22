package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.model.ContactMessage;
import com.kitnet.kitnet.repository.ContactMessageRepository;
import com.kitnet.kitnet.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Override
    public ContactMessage save(ContactMessage message) {
        return contactMessageRepository.save(message);
    }

    @Override
    public List<ContactMessage> getAll() {
        return contactMessageRepository.findAll();
    }

    @Override
    public ContactMessage getById(Long id) {
        return contactMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact Message not found with id: " + id));
    }
}
