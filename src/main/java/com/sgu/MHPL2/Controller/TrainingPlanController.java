package com.sgu.MHPL2.Controller;

import com.sgu.MHPL2.DTO.ApiResponse;
import com.sgu.MHPL2.DTO.ClassGroupDTO;
import com.sgu.MHPL2.DTO.TrainingPlanDTO;
import com.sgu.MHPL2.Service.ClassGroupService;
import com.sgu.MHPL2.Service.TrainingPlanService;
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
@RequestMapping("/api/training-plans")
@CrossOrigin(origins = "*")
public class TrainingPlanController {

    @Autowired
    private TrainingPlanService trainingPlanService;

    @Autowired
    private ClassGroupService classGroupService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TrainingPlanDTO>>> getAllTrainingPlans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir,
            @RequestParam(required = false) Integer semester,
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) String status
    ) {
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TrainingPlanDTO> trainingPlans = trainingPlanService.getTrainingPlansByFilters(
                semester, academicYear, status, pageable);

        ApiResponse<Page<TrainingPlanDTO>> response = new ApiResponse<>(
                trainingPlans,
                HttpStatus.OK.value(),
                "Lấy danh sách kế hoạch đào tạo thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<TrainingPlanDTO>>> searchTrainingPlans(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TrainingPlanDTO> trainingPlans = trainingPlanService.searchTrainingPlans(keyword, pageable);

        ApiResponse<Page<TrainingPlanDTO>> response = new ApiResponse<>(
                trainingPlans,
                HttpStatus.OK.value(),
                "Tìm kiếm kế hoạch đào tạo thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TrainingPlanDTO>> getTrainingPlanById(@PathVariable Integer id) {
        TrainingPlanDTO trainingPlan = trainingPlanService.getTrainingPlanById(id);
        if (trainingPlan == null) {
            ApiResponse<TrainingPlanDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy kế hoạch đào tạo với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<TrainingPlanDTO> response = new ApiResponse<>(
                trainingPlan,
                HttpStatus.OK.value(),
                "Lấy thông tin kế hoạch đào tạo thành công"
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TrainingPlanDTO>> createTrainingPlan(@Valid @RequestBody TrainingPlanDTO trainingPlanDTO) {
        TrainingPlanDTO createdTrainingPlan = trainingPlanService.createTrainingPlan(trainingPlanDTO);

        ApiResponse<TrainingPlanDTO> response = new ApiResponse<>(
                createdTrainingPlan,
                HttpStatus.CREATED.value(),
                "Tạo kế hoạch đào tạo thành công"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TrainingPlanDTO>> updateTrainingPlan(
            @PathVariable Integer id,
            @Valid @RequestBody TrainingPlanDTO trainingPlanDTO
    ) {
        TrainingPlanDTO updatedTrainingPlan = trainingPlanService.updateTrainingPlan(id, trainingPlanDTO);
        if (updatedTrainingPlan == null) {
            ApiResponse<TrainingPlanDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy kế hoạch đào tạo với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<TrainingPlanDTO> response = new ApiResponse<>(
                updatedTrainingPlan,
                HttpStatus.OK.value(),
                "Cập nhật kế hoạch đào tạo thành công"
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTrainingPlan(@PathVariable Integer id) {
        boolean deleted = trainingPlanService.deleteTrainingPlan(id);
        if (!deleted) {
            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy kế hoạch đào tạo với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<Void> response = new ApiResponse<>(
                null,
                HttpStatus.OK.value(),
                "Xóa kế hoạch đào tạo thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/class-groups")
    public ResponseEntity<ApiResponse<List<ClassGroupDTO>>> getClassGroupsByTrainingPlanId(@PathVariable Integer id) {
        // Check if training plan exists
        TrainingPlanDTO trainingPlan = trainingPlanService.getTrainingPlanById(id);
        if (trainingPlan == null) {
            ApiResponse<List<ClassGroupDTO>> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy kế hoạch đào tạo với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<ClassGroupDTO> classGroups = classGroupService.getClassGroupsByTrainingPlanId(id);

        ApiResponse<List<ClassGroupDTO>> response = new ApiResponse<>(
                classGroups,
                HttpStatus.OK.value(),
                "Lấy danh sách nhóm lớp theo kế hoạch đào tạo thành công"
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/class-groups")
    public ResponseEntity<ApiResponse<ClassGroupDTO>> addClassGroupToTrainingPlan(
            @PathVariable Integer id,
            @Valid @RequestBody ClassGroupDTO classGroupDTO
    ) {
        // Check if training plan exists
        TrainingPlanDTO trainingPlan = trainingPlanService.getTrainingPlanById(id);
        if (trainingPlan == null) {
            ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy kế hoạch đào tạo với id: " + id
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Set the training plan ID
        classGroupDTO.setTrainingPlanId(id);

        // Create class group
        ClassGroupDTO createdClassGroup = classGroupService.createClassGroup(classGroupDTO);

        ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                createdClassGroup,
                HttpStatus.CREATED.value(),
                "Thêm nhóm lớp vào kế hoạch đào tạo thành công"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{trainingPlanId}/class-groups/{classGroupId}")
    public ResponseEntity<ApiResponse<ClassGroupDTO>> updateClassGroupInTrainingPlan(
            @PathVariable Integer trainingPlanId,
            @PathVariable Integer classGroupId,
            @Valid @RequestBody ClassGroupDTO classGroupDTO
    ) {
        // Check if training plan exists
        TrainingPlanDTO trainingPlan = trainingPlanService.getTrainingPlanById(trainingPlanId);
        if (trainingPlan == null) {
            ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy kế hoạch đào tạo với id: " + trainingPlanId
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Check if class group exists and belongs to this training plan
        ClassGroupDTO existingClassGroup = classGroupService.getClassGroupById(classGroupId);
        if (existingClassGroup == null || !trainingPlanId.equals(existingClassGroup.getTrainingPlanId())) {
            ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy nhóm lớp với id: " + classGroupId + " trong kế hoạch đào tạo này"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Set the IDs
        classGroupDTO.setId(classGroupId);
        classGroupDTO.setTrainingPlanId(trainingPlanId);

        // Update class group
        ClassGroupDTO updatedClassGroup = classGroupService.updateClassGroup(classGroupId, classGroupDTO);

        ApiResponse<ClassGroupDTO> response = new ApiResponse<>(
                updatedClassGroup,
                HttpStatus.OK.value(),
                "Cập nhật nhóm lớp trong kế hoạch đào tạo thành công"
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{trainingPlanId}/class-groups/{classGroupId}")
    public ResponseEntity<ApiResponse<Void>> removeClassGroupFromTrainingPlan(
            @PathVariable Integer trainingPlanId,
            @PathVariable Integer classGroupId
    ) {
        // Check if class group exists and belongs to this training plan
        ClassGroupDTO existingClassGroup = classGroupService.getClassGroupById(classGroupId);
        if (existingClassGroup == null || !trainingPlanId.equals(existingClassGroup.getTrainingPlanId())) {
            ApiResponse<Void> response = new ApiResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "Không tìm thấy nhóm lớp với id: " + classGroupId + " trong kế hoạch đào tạo này"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Delete class group
        boolean deleted = classGroupService.deleteClassGroup(classGroupId);

        ApiResponse<Void> response = new ApiResponse<>(
                null,
                HttpStatus.OK.value(),
                "Xóa nhóm lớp khỏi kế hoạch đào tạo thành công"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-department/{department}")
    public ResponseEntity<ApiResponse<List<TrainingPlanDTO>>> getTrainingPlansByDepartment(@PathVariable String department) {
        List<TrainingPlanDTO> trainingPlans = trainingPlanService.getTrainingPlansByDepartment(department);

        ApiResponse<List<TrainingPlanDTO>> response = new ApiResponse<>(
                trainingPlans,
                HttpStatus.OK.value(),
                "Lấy danh sách kế hoạch đào tạo theo khoa thành công"
        );

        return ResponseEntity.ok(response);
    }
}
