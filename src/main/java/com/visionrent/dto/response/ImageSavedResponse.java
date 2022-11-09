package com.visionrent.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageSavedResponse extends VRResponse{

	private String imageId;
	
	//constructor; message ve succes bilgisini VRResponse constructor'ini  kullanarak set edecegiz
	public ImageSavedResponse(String imageId, String message, boolean success) {
		super(message,success);
		this.imageId=imageId;
		
	}
}
