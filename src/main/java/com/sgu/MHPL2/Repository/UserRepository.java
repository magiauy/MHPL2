package com.sgu.MHPL2.Repository;

import com.sgu.MHPL2.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Page<User> findByEmailContainingIgnoreCase(String keyword, Pageable pageable);
    Page<User> findByIsAdmin(Boolean isAdmin, Pageable pageable);
    Page<User> findByEmailContainingIgnoreCaseAndIsAdmin(String keyword, Boolean isAdmin, Pageable pageable);
}
