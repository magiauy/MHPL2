package com.sgu.MHPL2.Controller;

    import com.sgu.MHPL2.DTO.UserDTO;
    import com.sgu.MHPL2.Service.UserService;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Page;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/api/users")
    @RequiredArgsConstructor
    @Slf4j
    public class UserController {

        private final UserService userService;

        @PostMapping
        public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
            log.info("REST request to create user with email: {}", userDTO.getEmail());
            UserDTO createdUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }

        @GetMapping("/{id}")
        public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id) {
            log.info("REST request to get user by ID: {}", id);
            UserDTO user = userService.getUserById(id);
            return user != null
                    ? ResponseEntity.ok(user)
                    : ResponseEntity.notFound().build();
        }

        @GetMapping
        public ResponseEntity<List<UserDTO>> getAllUsers() {
            log.info("REST request to get all users");
            return ResponseEntity.ok(userService.getAllUsers());
        }

        @GetMapping("/email/{email}")
        public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
            log.info("REST request to get user by email: {}", email);
            UserDTO user = userService.getUserByEmail(email);
            return user != null
                    ? ResponseEntity.ok(user)
                    : ResponseEntity.notFound().build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<UserDTO> updateUser(@PathVariable("id") int id, @RequestBody UserDTO userDTO) {
            log.info("REST request to update user with ID: {}", id);
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return updatedUser != null
                    ? ResponseEntity.ok(updatedUser)
                    : ResponseEntity.notFound().build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
            log.info("REST request to delete user with ID: {}", id);
            boolean deleted = userService.deleteUser(id);
            return deleted
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        }

        @PostMapping("/login")
        public ResponseEntity<UserDTO> login(@RequestBody Map<String, String> credentials) {
            log.info("REST request to login user with email: {}", credentials.get("email"));
            UserDTO user = userService.login(credentials.get("email"), credentials.get("password"));
            return user != null
                    ? ResponseEntity.ok(user)
                    : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        @GetMapping("/paginated")
        public ResponseEntity<Page<UserDTO>> getUsersPaginated(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "id") String sortBy,
                @RequestParam(defaultValue = "ASC") String direction,
                @RequestParam(required = false) Boolean isAdmin,
                @RequestParam(required = false) String keyword) {

            log.info("REST request to get paginated users with page: {}, size: {}", page, size);
            Page<UserDTO> userPage = userService.getUsersPaginated(page, size, sortBy, direction, isAdmin, keyword);
            return ResponseEntity.ok(userPage);
        }
    }

