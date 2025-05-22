package com.kitnet.kitnet.service;

import com.kitnet.kitnet.model.ContactMessage;
import java.util.List;

public interface ContactMessageService {
    ContactMessage save(ContactMessage message);
    List<ContactMessage> getAll();
    ContactMessage getById(Long id);
}
