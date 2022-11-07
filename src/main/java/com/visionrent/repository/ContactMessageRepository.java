package com.visionrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visionrent.domain.ContactMessage;

@Repository
public interface ContactMessageRepository  extends JpaRepository<ContactMessage, Long>{

}
