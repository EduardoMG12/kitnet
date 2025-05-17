package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
