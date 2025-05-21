package com.sgu.MHPL2.Controller;

import com.sgu.MHPL2.DTO.ApiResponse;
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
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        try {
            log.info("REST request to create user with email: {}", userDTO.getEmail());
            UserDTO createdUser = userService.createUser(userDTO);

            ApiResponse<UserDTO> response = new ApiResponse<>(
                    createdUser,
                    HttpStatus.CREATED.value(),
                    "Tạo người dùng thành công"
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error creating user: ", e);
            ApiResponse<UserDTO> errorResponse = new ApiResponse<>(
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi hệ thống: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable("id") int id) {
        try {
            log.info("REST request to get user by ID: {}", id);
            UserDTO user = userService.getUserById(id);

            if (user == null) {
                ApiResponse<UserDTO> response = new ApiResponse<>(
                        null,
                        HttpStatus.NOT_FOUND.value(),
                        "Không tìm thấy người dùng với id: " + id
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ApiResponse<UserDTO> response = new ApiResponse<>(
                    user,
                    HttpStatus.OK.value(),
                    "Lấy thông tin người dùng thành công"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting user by ID: ", e);
            ApiResponse<UserDTO> errorResponse = new ApiResponse<>(
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi hệ thống: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        try {
            log.info("REST request to get all users");
            List<UserDTO> users = userService.getAllUsers();

            ApiResponse<List<UserDTO>> response = new ApiResponse<>(
                    users,
                    HttpStatus.OK.value(),
                    "Lấy danh sách người dùng thành công"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting all users: ", e);
            ApiResponse<List<UserDTO>> errorResponse = new ApiResponse<>(
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi hệ thống: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        try {
            log.info("REST request to get user by email: {}", email);
            UserDTO user = userService.getUserByEmail(email);

            if (user == null) {
                ApiResponse<UserDTO> response = new ApiResponse<>(
                        null,
                        HttpStatus.NOT_FOUND.value(),
                        "Không tìm thấy người dùng với email: " + email
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ApiResponse<UserDTO> response = new ApiResponse<>(
                    user,
                    HttpStatus.OK.value(),
                    "Lấy thông tin người dùng theo email thành công"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting user by email: ", e);
            ApiResponse<UserDTO> errorResponse = new ApiResponse<>(
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi hệ thống: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable("id") int id, @RequestBody UserDTO userDTO) {
        try {
            log.info("REST request to update user with ID: {}", id);
            UserDTO updatedUser = userService.updateUser(id, userDTO);

            if (updatedUser == null) {
                ApiResponse<UserDTO> response = new ApiResponse<>(
                        null,
                        HttpStatus.NOT_FOUND.value(),
                        "Không tìm thấy người dùng với id: " + id
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ApiResponse<UserDTO> response = new ApiResponse<>(
                    updatedUser,
                    HttpStatus.OK.value(),
                    "Cập nhật người dùng thành công"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating user: ", e);
            ApiResponse<UserDTO> errorResponse = new ApiResponse<>(
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi hệ thống: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") int id) {
        try {
            log.info("REST request to delete user with ID: {}", id);
            boolean deleted = userService.deleteUser(id);

            if (!deleted) {
                ApiResponse<Void> response = new ApiResponse<>(
                        null,
                        HttpStatus.NOT_FOUND.value(),
                        "Không tìm thấy người dùng với id: " + id
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    HttpStatus.OK.value(),
                    "Xóa người dùng thành công"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error deleting user: ", e);
            ApiResponse<Void> errorResponse = new ApiResponse<>(
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi hệ thống: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody Map<String, String> credentials) {
        try {
            log.info("REST request to login user with email: {}", credentials.get("email"));
            UserDTO user = userService.login(credentials.get("email"), credentials.get("password"));

            if (user == null) {
                ApiResponse<UserDTO> response = new ApiResponse<>(
                        null,
                        HttpStatus.UNAUTHORIZED.value(),
                        "Đăng nhập không thành công, email hoặc mật khẩu không đúng"
                );
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            ApiResponse<UserDTO> response = new ApiResponse<>(
                    user,
                    HttpStatus.OK.value(),
                    "Đăng nhập thành công"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error during login: ", e);
            ApiResponse<UserDTO> errorResponse = new ApiResponse<>(
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi hệ thống: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getUsersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) Boolean isAdmin,
            @RequestParam(required = false) String keyword) {
        try {
            log.info("REST request to get paginated users with page: {}, size: {}", page, size);
            Page<UserDTO> userPage = userService.getUsersPaginated(page, size, sortBy, direction, isAdmin, keyword);

            ApiResponse<Page<UserDTO>> response = new ApiResponse<>(
                    userPage,
                    HttpStatus.OK.value(),
                    "Lấy danh sách người dùng phân trang thành công"
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting paginated users: ", e);
            ApiResponse<Page<UserDTO>> errorResponse = new ApiResponse<>(
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Lỗi hệ thống: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
