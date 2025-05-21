package com.sgu.MHPL2.Controller;

import com.sgu.MHPL2.DTO.ApiResponse;
import com.sgu.MHPL2.DTO.TeacherDTO;
import com.sgu.MHPL2.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<ApiResponse<TeacherDTO>> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        log.info("REST request to create teacher with name: {}", teacherDTO.getName());
        TeacherDTO createdTeacher = teacherService.createTeacher(teacherDTO);

        ApiResponse<TeacherDTO> response = new ApiResponse<>(
                createdTeacher,
                HttpStatus.CREATED.value(),
                "Tạo giảng viên thành công"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDTO>> getTeacherById(@PathVariable("id") Integer id) {
        log.info("REST request to get teacher by ID: {}", id);
        TeacherDTO teacher = teacherService.getTeacherById(id);

        if (teacher == null) {
            ApiResponse<TeacherDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy giảng viên với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<TeacherDTO> response = new ApiResponse<>(
                teacher,
                HttpStatus.OK.value(),
                "Lấy thông tin giảng viên thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> getAllTeachers() {
        log.info("REST request to get all teachers");
        List<TeacherDTO> teachers = teacherService.getAllTeachers();

        ApiResponse<List<TeacherDTO>> response = new ApiResponse<>(
                teachers,
                HttpStatus.OK.value(),
                "Lấy danh sách giảng viên thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/position/{positionId}")
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> getTeachersByPosition(@PathVariable Integer positionId) {
        log.info("REST request to get teachers by position ID: {}", positionId);
        List<TeacherDTO> teachers = teacherService.getTeachersByPosition(positionId);

        ApiResponse<List<TeacherDTO>> response = new ApiResponse<>(
                teachers,
                HttpStatus.OK.value(),
                "Lấy danh sách giảng viên theo chức vụ thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> getActiveTeachers() {
        log.info("REST request to get active teachers");
        List<TeacherDTO> teachers = teacherService.getActiveTeachers();

        ApiResponse<List<TeacherDTO>> response = new ApiResponse<>(
                teachers,
                HttpStatus.OK.value(),
                "Lấy danh sách giảng viên đang hoạt động thành công"
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDTO>> updateTeacher(@PathVariable("id") Integer id, @RequestBody TeacherDTO teacherDTO) {
        log.info("REST request to update teacher with ID: {}", id);
        TeacherDTO updatedTeacher = teacherService.updateTeacher(id, teacherDTO);

        if (updatedTeacher == null) {
            ApiResponse<TeacherDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy giảng viên với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<TeacherDTO> response = new ApiResponse<>(
                updatedTeacher,
                HttpStatus.OK.value(),
                "Cập nhật giảng viên thành công"
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTeacher(@PathVariable("id") Integer id) {
        log.info("REST request to delete teacher with ID: {}", id);
        boolean deleted = teacherService.deleteTeacher(id);

        if (!deleted) {
            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy giảng viên với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<Void> response = new ApiResponse<>(
                null,
                HttpStatus.OK.value(),
                "Xóa giảng viên thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<TeacherDTO>>> getTeachersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String keyword) {

        log.info("REST request to get paginated teachers with page: {}, size: {}", page, size);
        Page<TeacherDTO> teacherPage = teacherService.getTeachersPaginated(page, size, sortBy, direction, status, keyword);

        ApiResponse<Page<TeacherDTO>> response = new ApiResponse<>(
                teacherPage,
                HttpStatus.OK.value(),
                "Lấy danh sách giảng viên phân trang thành công"
        );

        return ResponseEntity.ok(response);
    }
}
