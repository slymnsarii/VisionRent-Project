package com.visionrent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visionrent.domain.ImageFile;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, String>{

	@EntityGraph(attributePaths = "id") //parametreye id degeri girildigi zaman, ayni seviyedeki datalar gelir,
										//bagli oldugu imageData'lar gelmemis olacak
	List<ImageFile> findAll();
	
	@EntityGraph(attributePaths = "id") //imageFile ile ilgili data'lar gelsin
	Optional<ImageFile> findImageById(String id);
}
