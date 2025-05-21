package com.sgu.MHPL2.Controller;

import com.sgu.MHPL2.DTO.ApiResponse;
import com.sgu.MHPL2.DTO.SubjectTypeDTO;
import com.sgu.MHPL2.Service.SubjectTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject-types")
@RequiredArgsConstructor
@Slf4j
public class SubjectTypeController {

    private final SubjectTypeService subjectTypeService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectTypeDTO>> createSubjectType(@RequestBody SubjectTypeDTO subjectTypeDTO) {
        log.info("REST request to create subject type with name: {}", subjectTypeDTO.getName());
        SubjectTypeDTO createdSubjectType = subjectTypeService.createSubjectType(subjectTypeDTO);

        ApiResponse<SubjectTypeDTO> response = new ApiResponse<>(
                createdSubjectType,
                HttpStatus.CREATED.value(),
                "Tạo loại học phần thành công"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectTypeDTO>> getSubjectTypeById(@PathVariable("id") int id) {
        log.info("REST request to get subject type by ID: {}", id);
        SubjectTypeDTO subjectType = subjectTypeService.getSubjectTypeById(id);

        if (subjectType == null) {
            ApiResponse<SubjectTypeDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy loại học phần với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<SubjectTypeDTO> response = new ApiResponse<>(
                subjectType,
                HttpStatus.OK.value(),
                "Lấy thông tin loại học phần thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectTypeDTO>>> getAllSubjectTypes() {
        log.info("REST request to get all subject types");
        List<SubjectTypeDTO> subjectTypes = subjectTypeService.getAllSubjectTypes();

        ApiResponse<List<SubjectTypeDTO>> response = new ApiResponse<>(
                subjectTypes,
                HttpStatus.OK.value(),
                "Lấy danh sách loại học phần thành công"
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectTypeDTO>> updateSubjectType(@PathVariable("id") int id, @RequestBody SubjectTypeDTO subjectTypeDTO) {
        log.info("REST request to update subject type with ID: {}", id);
        SubjectTypeDTO updatedSubjectType = subjectTypeService.updateSubjectType(id, subjectTypeDTO);

        if (updatedSubjectType == null) {
            ApiResponse<SubjectTypeDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy loại học phần với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<SubjectTypeDTO> response = new ApiResponse<>(
                updatedSubjectType,
                HttpStatus.OK.value(),
                "Cập nhật loại học phần thành công"
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubjectType(@PathVariable("id") int id) {
        log.info("REST request to delete subject type with ID: {}", id);
        boolean deleted = subjectTypeService.deleteSubjectType(id);

        if (!deleted) {
            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy loại học phần với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<Void> response = new ApiResponse<>(
                null,
                HttpStatus.OK.value(),
                "Xóa loại học phần thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    public ResponseEntity<ApiResponse<Page<SubjectTypeDTO>>> getSubjectTypesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) String keyword) {

        log.info("REST request to get paginated subject types with page: {}, size: {}", page, size);
        Page<SubjectTypeDTO> subjectTypePage = subjectTypeService.getSubjectTypesPaginated(page, size, sortBy, direction, keyword);

        ApiResponse<Page<SubjectTypeDTO>> response = new ApiResponse<>(
                subjectTypePage,
                HttpStatus.OK.value(),
                "Lấy danh sách loại học phần phân trang thành công"
        );

        return ResponseEntity.ok(response);
    }
}
