package com.visionrent.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.visionrent.domain.ImageData;
import com.visionrent.domain.ImageFile;
import com.visionrent.dto.ImageFileDTO;
import com.visionrent.exception.ResourceNotFoundException;
import com.visionrent.exception.message.ErrorMessage;
import com.visionrent.repository.ImageFileRepository;

@Service
public class ImageFileService {
	
	@Autowired
	private ImageFileRepository imageFileRepository;

	public String saveImage(MultipartFile file) {
		
		ImageFile imageFile = null;
		// name kısımı
		String fileName= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		
		try {
			ImageData imData= new ImageData(file.getBytes());
			imageFile = new ImageFile(fileName, file.getContentType(), imData);
			
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		imageFileRepository.save(imageFile);
		
		return imageFile.getId();

		
	}

	public ImageFile getImageById(String id) {
		
		ImageFile imageFile = imageFileRepository.findById(id).orElseThrow(()-> 
		new ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, id)));
		
		return imageFile;
		
	}
//*****************************************
	
	public List<ImageFileDTO> getAllImages() {
		
		List<ImageFile > imageFiles =  imageFileRepository.findAll();
		
		 List<ImageFileDTO> imageFileDTOs =imageFiles.stream().map(imFile->{
			//URI oluşturmamız gerekiyor
			     String imageUri =  ServletUriComponentsBuilder. // Uri build ede nhazır metod
			      fromCurrentContextPath().// localhost:8080
			      path("/files/download/").
			      path(imFile.getId()).toUriString();// end-point sonuna image id eklendi   --> localhost:8080/files/download/3
			     
			     return new ImageFileDTO(imFile.getName(), imageUri,imFile.getType(),imFile.getLength());
		}).collect(Collectors.toList());
		 
		 return imageFileDTOs;
	}

	//*************************************************
	
	public void removeById(String id) {
		
		 ImageFile imageFile =  getImageById(id);
		 imageFileRepository.delete(imageFile);
	}
	
	//******************************************
	
	// car tarafı için gerekli metod
	public ImageFile findImageById(String id) {
		return imageFileRepository.findImageById(id).orElseThrow(()-> new
				ResourceNotFoundException(String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, id)));
	}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
