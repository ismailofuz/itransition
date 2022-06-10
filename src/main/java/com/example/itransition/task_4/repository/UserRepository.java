package com.example.itransition.task_4.repository;

import com.example.itransition.task_4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query(value="select * from user u where u.email= :email and status=true", nativeQuery=true)
	User findByEmail(String email);
	void deleteById(Long id);
}
