package com.visionrent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visionrent.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Boolean existsByEmail(String email);
	
	
	// User ve Role arasında ManyToMany ilişkide default olarak LAZY tanımlıydı,
	//biz bunu EAGER olmasını sağladık @EntityGraph ile
	@EntityGraph(attributePaths = "roles")
	Optional<User> findByEmail(String email);
	
	

}
