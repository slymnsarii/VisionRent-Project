package com.visionrent.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.visionrent.domain.ContactMessage;
import com.visionrent.dto.ContactMessageDTO;
import com.visionrent.dto.request.ContactMessageRequest;

@Mapper(componentModel = "spring") //herhangi bir sinif enjekte edip kullanabilirim
public interface ContactMessageMapper {
	
	//ContactMessage --> ContactMesasgeDTO
	ContactMessageDTO contactMessageToDTO(ContactMessage contactMessage);
	
	//ContactMessageRequest --> ContactMessage
	@Mapping(target = "id", ignore = true) //target'ta ki id field'ini mapp'leme
	ContactMessage contactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest);
	
	//List<ContactMessage> --> List<ContactMessageDTO>
	List<ContactMessageDTO> map(List<ContactMessage> contactMessageList);
}
