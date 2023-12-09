package com.decagon.demo1.Repository;

import com.decagon.demo1.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Users, Long> {
Optional<Users> findUsersByUsername(String username);

}
