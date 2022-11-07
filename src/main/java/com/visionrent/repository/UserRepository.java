package com.visionrent.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.visionrent.domain.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	   Boolean existsByEmail(String email);
	  
	  
	// User ve Role arasında ManyToMany ilişkide default olarak LAZY tanımlıydı,
		//			biz bunu EAGER olmasını sağladık @EntityGraph ile
	  
	   @EntityGraph(attributePaths = "roles")
	   Optional<User> findByEmail(String email);
	  
	   @EntityGraph(attributePaths = "roles")
	   List<User> findAll();    // role bilgisi LAZY tanımlandığı için her rolde SQL kodu
	   //oluşmasını istemediğimden @EntityGRaph annotationı ile fetch type ını EAGER yapmış olduk
	  
	   @EntityGraph(attributePaths = "roles")
	   Page<User> findAll(Pageable pageable);
	  
	   @EntityGraph(attributePaths = "roles")
	   Optional<User> findById(Long id);
	  
}