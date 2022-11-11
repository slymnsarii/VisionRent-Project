package com.visionrent.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor     // int 
@NoArgsConstructor

@Entity
@Table(name ="t_imagedata")
public class ImageData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Lob
	private byte[] data; // image datası byte array şeklinde
	
	public ImageData(byte[] data){
		this.data= data;
	}
	
	public ImageData(Long id) {
		this.id=id;
	}

}
