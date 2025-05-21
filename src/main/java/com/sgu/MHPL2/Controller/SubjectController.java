package com.sgu.MHPL2.Controller;

import com.sgu.MHPL2.DTO.ApiResponse;
import com.sgu.MHPL2.DTO.SubjectDTO;
import com.sgu.MHPL2.Service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectDTO>> createSubject(@RequestBody SubjectDTO subjectDTO) {
        log.info("REST request to create subject with name: {}", subjectDTO.getName());
        SubjectDTO createdSubject = subjectService.createSubject(subjectDTO);

        ApiResponse<SubjectDTO> response = new ApiResponse<>(
                createdSubject,
                HttpStatus.CREATED.value(),
                "Tạo học phần thành công"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectDTO>> getSubjectById(@PathVariable("id") Integer id) {
        log.info("REST request to get subject by ID: {}", id);
        SubjectDTO subject = subjectService.getSubjectById(id);

        if (subject == null) {
            ApiResponse<SubjectDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy học phần với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<SubjectDTO> response = new ApiResponse<>(
                subject,
                HttpStatus.OK.value(),
                "Lấy thông tin học phần thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getAllSubjects() {
        log.info("REST request to get all subjects");
        List<SubjectDTO> subjects = subjectService.getAllSubjects();

        ApiResponse<List<SubjectDTO>> response = new ApiResponse<>(
                subjects,
                HttpStatus.OK.value(),
                "Lấy danh sách học phần thành công"
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectDTO>> updateSubject(@PathVariable("id") Integer id, @RequestBody SubjectDTO subjectDTO) {
        log.info("REST request to update subject with ID: {}", id);
        SubjectDTO updatedSubject = subjectService.updateSubject(id, subjectDTO);

        if (updatedSubject == null) {
            ApiResponse<SubjectDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy học phần với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<SubjectDTO> response = new ApiResponse<>(
                updatedSubject,
                HttpStatus.OK.value(),
                "Cập nhật học phần thành công"
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubject(@PathVariable("id") Integer id) {
        log.info("REST request to delete subject with ID: {}", id);
        boolean deleted = subjectService.deleteSubject(id);

        if (!deleted) {
            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy học phần với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<Void> response = new ApiResponse<>(
                null,
                HttpStatus.OK.value(),
                "Xóa học phần thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getSubjectsByType(@PathVariable Integer typeId) {
        log.info("REST request to get subjects by type ID: {}", typeId);
        List<SubjectDTO> subjects = subjectService.getSubjectsByType(typeId);

        ApiResponse<List<SubjectDTO>> response = new ApiResponse<>(
                subjects,
                HttpStatus.OK.value(),
                "Lấy danh sách học phần theo loại thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/required")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getRequiredSubjects() {
        log.info("REST request to get required subjects");
        List<SubjectDTO> subjects = subjectService.getRequiredSubjects();

        ApiResponse<List<SubjectDTO>> response = new ApiResponse<>(
                subjects,
                HttpStatus.OK.value(),
                "Lấy danh sách học phần bắt buộc thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/prerequisite/{code}")
    public ResponseEntity<ApiResponse<SubjectDTO>> getSubjectByPrerequisiteCode(@PathVariable String code) {
        log.info("REST request to get subject by prerequisite code: {}", code);
        SubjectDTO subject = subjectService.getSubjectByPrerequisiteCode(code);

        if (subject == null) {
            ApiResponse<SubjectDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy học phần với mã điều kiện: " + code
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<SubjectDTO> response = new ApiResponse<>(
                subject,
                HttpStatus.OK.value(),
                "Lấy thông tin học phần theo mã điều kiện thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<SubjectDTO>>> getSubjectsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) Boolean isRequired,
            @RequestParam(required = false) String keyword) {

        log.info("REST request to get paginated subjects with page: {}, size: {}", page, size);
        Page<SubjectDTO> subjectPage = subjectService.getSubjectsPaginated(
                page, size, sortBy, direction, isRequired, keyword);

        ApiResponse<Page<SubjectDTO>> response = new ApiResponse<>(
                subjectPage,
                HttpStatus.OK.value(),
                "Lấy danh sách học phần phân trang thành công"
        );

        return ResponseEntity.ok(response);
    }
}
