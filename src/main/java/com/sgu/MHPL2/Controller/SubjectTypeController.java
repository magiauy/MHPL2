package com.sgu.MHPL2.Controller;

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
    public ResponseEntity<SubjectTypeDTO> createSubjectType(@RequestBody SubjectTypeDTO subjectTypeDTO) {
        log.info("REST request to create subject type with name: {}", subjectTypeDTO.getName());
        SubjectTypeDTO createdSubjectType = subjectTypeService.createSubjectType(subjectTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubjectType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectTypeDTO> getSubjectTypeById(@PathVariable("id") int id) {
        log.info("REST request to get subject type by ID: {}", id);
        SubjectTypeDTO subjectType = subjectTypeService.getSubjectTypeById(id);
        return subjectType != null
                ? ResponseEntity.ok(subjectType)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<SubjectTypeDTO>> getAllSubjectTypes() {
        log.info("REST request to get all subject types");
        return ResponseEntity.ok(subjectTypeService.getAllSubjectTypes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectTypeDTO> updateSubjectType(@PathVariable("id") int id, @RequestBody SubjectTypeDTO subjectTypeDTO) {
        log.info("REST request to update subject type with ID: {}", id);
        SubjectTypeDTO updatedSubjectType = subjectTypeService.updateSubjectType(id, subjectTypeDTO);
        return updatedSubjectType != null
                ? ResponseEntity.ok(updatedSubjectType)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubjectType(@PathVariable("id") int id) {
        log.info("REST request to delete subject type with ID: {}", id);
        boolean deleted = subjectTypeService.deleteSubjectType(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<SubjectTypeDTO>> getSubjectTypesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) String keyword) {

        log.info("REST request to get paginated subject types with page: {}, size: {}", page, size);
        Page<SubjectTypeDTO> subjectTypePage = subjectTypeService.getSubjectTypesPaginated(page, size, sortBy, direction, keyword);
        return ResponseEntity.ok(subjectTypePage);
    }
}
