package com.kitnet.kitnet.controller;

import com.kitnet.kitnet.model.ContactMessage;
import com.kitnet.kitnet.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/contact-messages")
public class ContactMessageController {

    @Autowired
    private ContactMessageService contactMessageService;

    @PostMapping
    public ContactMessage create(@RequestBody ContactMessage message) {
        message.setSentAt(LocalDateTime.now());
        return contactMessageService.save(message);
    }

    @GetMapping
    public List<ContactMessage> getAll() {
        return contactMessageService.getAll();
    }

    @GetMapping("/{id}")
    public ContactMessage getById(@PathVariable Long id) {
        return contactMessageService.getById(id);
    }
}
