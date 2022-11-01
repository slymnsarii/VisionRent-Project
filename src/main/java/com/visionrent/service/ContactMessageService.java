package com.visionrent.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.visionrent.domain.ContactMessage;
import com.visionrent.repository.ContactMessageRepository;
@Service
public class ContactMessageService {
	
	private ContactMessageRepository contactMessageRepository;
	
	
@Autowired
	public ContactMessageService(ContactMessageRepository contactMessageRepository) {
		this.contactMessageRepository = contactMessageRepository;
	}
	public void saveMessage(ContactMessage contactMessage) {
		contactMessageRepository.save(contactMessage);
		
	}
	public List<ContactMessage> getAll() {
		return contactMessageRepository.findAll();
	}
	public Page<ContactMessage> getAll(Pageable pageable) {
		 return contactMessageRepository.findAll(pageable);
	}
}