package com.decagon.demo1.Repository;

import com.decagon.demo1.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {
      Optional<Users> findUserByUsername(String username);
}
