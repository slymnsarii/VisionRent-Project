package com.visionrent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	   //oluşmasını istemediğimden @EntityGraph annotationı ile fetch type ını EAGER yapmış olduk
	   
	   @EntityGraph(attributePaths = "roles")
	   Page<User> findAll(Pageable pageable);
	   
	   @EntityGraph(attributePaths = "roles")
	   Optional<User> findById(Long id);
	   
	   //parantez içine id yazdık, böylelikle roller gelmemiş oluyor,
	   // user ve roller ilişkili iken ben sadece userlar gelsin istiyorsam 
	   @EntityGraph(attributePaths = "id")
	   Optional<User> findUserById(Long id);
	   
	   @Modifying // JpaRepository içinde custom bir query ile DML operasyonları yapılıyor ise  @Modifying konulur
	   @Query( "UPDATE User u SET u.firstName=:firstName, u.lastName=:lastName,u.phoneNumber=:phoneNumber,u.email=:email,u.address=:address,u.zipCode=:zipCode WHERE u.id=:id"    )
	   void update(@Param("id") Long id,
			   					 @Param("firstName") String firstName,
			   					 @Param("lastName") String lastName,
			   					 @Param("phoneNumber") String phoneNumber,
			   					 @Param("email") String email,
			   					 @Param("address") String address,
			   					  @Param("zipCode") String zipCode);

}
