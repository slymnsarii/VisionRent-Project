package com.visionrent.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.visionrent.domain.ContactMessage;
import com.visionrent.dto.ContactMessageDTO;
import com.visionrent.dto.request.ContactMessageRequest;

@Mapper(componentModel = "spring") // herhangi bir sınıf enjekte edip kullanabilirim
public interface ContactMessageMapper {
	
	
	
	// ContactMessage ---> ContactMessageDTO
	ContactMessageDTO contactMessageToDTO(ContactMessage contactMessage);
	
	/*
	contactMessageDTo.name = contactMessage.name;
	contactMessageDTo.subject = contactMessage.subject;
	
	*/
	
	// ContactMessageRequest --> ContactMessage
	@Mapping(target="id", ignore=true)  // targetta ki id fieldını mappleme
	ContactMessage contactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest);
	
	// List<ContactMessage> --> List<ContactMessageDTO>
	List<ContactMessageDTO> map(List<ContactMessage> contactMessageList);
	
	

}
