package com.sgu.MHPL2.Controller;

import com.sgu.MHPL2.DTO.ApiResponse;
import com.sgu.MHPL2.DTO.ClassGroupDTO;
import com.sgu.MHPL2.DTO.TeacherAssignmentDTO;
import com.sgu.MHPL2.Model.TeacherAssignment;
import com.sgu.MHPL2.Service.ClassGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-groups")
@CrossOrigin(origins = "*")
public class ClassGroupController {

    @Autowired
    private ClassGroupService classGroupService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ClassGroupDTO>>> getAllClassGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) Integer semester,
            @RequestParam(required = false) String schoolYear,
            @RequestParam(required = false) String status
    ) {
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ClassGroupDTO> classGroups;
        if (semester != null || schoolYear != null || status != null) {
            classGroups = classGroupService.getClassGroupsByFilters(semester, schoolYear, status, pageable);
        } else {
            classGroups = classGroupService.getClassGroupsByFilters(null, null, null, pageable);
        }

        ApiResponse<Page<ClassGroupDTO>> response = new ApiResponse<>(
                classGroups,
                HttpStatus.OK.value(),
                "Lấy danh sách nhóm lớp thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ClassGroupDTO>>> searchClassGroups(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ClassGroupDTO> classGroups = classGroupService.searchClassGroups(keyword, pageable);

        ApiResponse<Page<ClassGroupDTO>> response = new ApiResponse<>(
                classGroups,
                HttpStatus.OK.value(),
                "Tìm kiếm nhóm lớp thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassGroupDTO>> getClassGroupById(@PathVariable Integer id) {
        ClassGroupDTO classGroup = classGroupService.getClassGroupById(id);
        if (classGroup == null) {
            ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy nhóm lớp với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                classGroup,
                HttpStatus.OK.value(),
                "Lấy thông tin nhóm lớp thành công"
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClassGroupDTO>> createClassGroup(@Valid @RequestBody ClassGroupDTO classGroupDTO) {
        ClassGroupDTO createdClassGroup = classGroupService.createClassGroup(classGroupDTO);

        ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                createdClassGroup,
                HttpStatus.CREATED.value(),
                "Tạo nhóm lớp thành công"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassGroupDTO>> updateClassGroup(
            @PathVariable Integer id,
            @Valid @RequestBody ClassGroupDTO classGroupDTO
    ) {
        ClassGroupDTO updatedClassGroup = classGroupService.updateClassGroup(id, classGroupDTO);
        if (updatedClassGroup == null) {
            ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy nhóm lớp với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                updatedClassGroup,
                HttpStatus.OK.value(),
                "Cập nhật nhóm lớp thành công"
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClassGroup(@PathVariable Integer id) {
        boolean deleted = classGroupService.deleteClassGroup(id);
        if (!deleted) {
            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy nhóm lớp với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<Void> response = new ApiResponse<>(
                null,
                HttpStatus.OK.value(),
                "Xóa nhóm lớp thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-subject/{subjectId}")
    public ResponseEntity<ApiResponse<List<ClassGroupDTO>>> getClassGroupsBySubjectId(@PathVariable Integer subjectId) {
        List<ClassGroupDTO> classGroups = classGroupService.getClassGroupsBySubjectId(subjectId);

        ApiResponse<List<ClassGroupDTO>> response = new ApiResponse<>(
                classGroups,
                HttpStatus.OK.value(),
                "Lấy danh sách nhóm lớp theo học phần thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-teacher/{teacherId}")
    public ResponseEntity<ApiResponse<List<ClassGroupDTO>>> getClassGroupsByTeacherId(@PathVariable Integer teacherId) {
        List<ClassGroupDTO> classGroups = classGroupService.getClassGroupsByTeacherId(teacherId);

        ApiResponse<List<ClassGroupDTO>> response = new ApiResponse<>(
                classGroups,
                HttpStatus.OK.value(),
                "Lấy danh sách nhóm lớp theo giảng viên thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-training-plan/{trainingPlanId}")
    public ResponseEntity<ApiResponse<List<ClassGroupDTO>>> getClassGroupsByTrainingPlanId(@PathVariable Integer trainingPlanId) {
        List<ClassGroupDTO> classGroups = classGroupService.getClassGroupsByTrainingPlanId(trainingPlanId);

        ApiResponse<List<ClassGroupDTO>> response = new ApiResponse<>(
                classGroups,
                HttpStatus.OK.value(),
                "Lấy danh sách nhóm lớp theo kế hoạch đào tạo thành công"
        );

        return ResponseEntity.ok(response);
    }

    // Endpoints để quản lý phân công giảng viên

    @PostMapping("/{classGroupId}/teachers")
    public ResponseEntity<ApiResponse<ClassGroupDTO>> addTeacherToClassGroup(
            @PathVariable Integer classGroupId,
            @Valid @RequestBody TeacherAssignmentDTO teacherAssignmentDTO
    ) {
        // Gán ID nhóm lớp
        teacherAssignmentDTO.setClassGroupId(classGroupId);

        ClassGroupDTO classGroupDTO = classGroupService.addTeacherAssignment(teacherAssignmentDTO);
        if (classGroupDTO == null) {
            ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy nhóm lớp với id: " + classGroupId
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                classGroupDTO,
                HttpStatus.CREATED.value(),
                "Thêm giảng viên vào nhóm lớp thành công"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{classGroupId}/teachers/{assignmentId}")
    public ResponseEntity<ApiResponse<ClassGroupDTO>> updateTeacherAssignment(
            @PathVariable Integer classGroupId,
            @PathVariable Integer assignmentId,
            @Valid @RequestBody TeacherAssignmentDTO teacherAssignmentDTO
    ) {
        // Gán các ID
        teacherAssignmentDTO.setId(assignmentId);
        teacherAssignmentDTO.setClassGroupId(classGroupId);

        ClassGroupDTO classGroupDTO = classGroupService.updateTeacherAssignment(teacherAssignmentDTO);
        if (classGroupDTO == null) {
            ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy phân công giảng viên hoặc nhóm lớp"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                classGroupDTO,
                HttpStatus.OK.value(),
                "Cập nhật phân công giảng viên thành công"
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{classGroupId}/teachers/{assignmentId}")
    public ResponseEntity<ApiResponse<Void>> removeTeacherFromClassGroup(
            @PathVariable Integer classGroupId,
            @PathVariable Integer assignmentId
    ) {
        boolean removed = classGroupService.removeTeacherAssignment(classGroupId, assignmentId);
        if (!removed) {
            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy phân công giảng viên hoặc nhóm lớp"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<Void> response = new ApiResponse<>(
                null,
                HttpStatus.OK.value(),
                "Xóa phân công giảng viên thành công"
        );

        return ResponseEntity.ok(response);
    }
}
