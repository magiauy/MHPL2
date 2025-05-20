package com.sgu.MHPL2.Service;

import com.sgu.MHPL2.DTO.UserDTO;
import com.sgu.MHPL2.DTO.UserMapper;
import com.sgu.MHPL2.Model.User;
import com.sgu.MHPL2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Create
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating new user with email: {}", userDTO.getEmail());
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    // Read
    public UserDTO getUserById(int id) {
        log.info("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElse(null);
    }

    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);
        return userRepository.findByEmail(email)
                .map(userMapper::toDTO)
                .orElse(null);
    }

    // Update
    public UserDTO updateUser(int id, UserDTO userDTO) {
        return userRepository.findById(id)
            .map(existingUser -> {
                // Update fields but preserve createdAt
                // DO NOT: existingUser.setCreatedAt(null);
                existingUser.setEmail(userDTO.getEmail());
                existingUser.setPassword(userDTO.getPassword());
                // Update other fields as needed

                return userMapper.toDTO(userRepository.save(existingUser));
            })
            .orElse(null);
    }

    // Delete
    public boolean deleteUser(int id) {
        log.info("Deleting user with ID: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Login
    public UserDTO login(String email, String password) {
        log.info("Attempting login for user: {}", email);
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                log.info("Login successful for user: {}", email);
                return userMapper.toDTO(user);
            }
            log.warn("Invalid password for user: {}", email);
        } else {
            log.warn("User not found with email: {}", email);
        }

        return null;
    }
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersPaginated(
            int page,
            int size,
            String sortBy,
            String direction,
            Boolean isAdmin,
            String keyword) {

        Sort sort = Sort.by(sortBy);
        if (direction.equalsIgnoreCase("DESC")) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Search with keyword
            if (isAdmin != null) {
                users = userRepository.findByEmailContainingIgnoreCaseAndIsAdmin(keyword, isAdmin, pageable);
            } else {
                users = userRepository.findByEmailContainingIgnoreCase(keyword, pageable);
            }
        } else {
            // No keyword search
            if (isAdmin != null) {
                users = userRepository.findByIsAdmin(isAdmin, pageable);
            } else {
                users = userRepository.findAll(pageable);
            }
        }

        return users.map(userMapper::toDTO);
    }
}