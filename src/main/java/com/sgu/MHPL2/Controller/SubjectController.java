package com.sgu.MHPL2.Controller;

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
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectDTO subjectDTO) {
        log.info("REST request to create subject with name: {}", subjectDTO.getName());
        SubjectDTO createdSubject = subjectService.createSubject(subjectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable("id") Integer id) {
        log.info("REST request to get subject by ID: {}", id);
        SubjectDTO subject = subjectService.getSubjectById(id);
        return subject != null
                ? ResponseEntity.ok(subject)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        log.info("REST request to get all subjects");
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateSubject(@PathVariable("id") Integer id, @RequestBody SubjectDTO subjectDTO) {
        log.info("REST request to update subject with ID: {}", id);
        SubjectDTO updatedSubject = subjectService.updateSubject(id, subjectDTO);
        return updatedSubject != null
                ? ResponseEntity.ok(updatedSubject)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("id") Integer id) {
        log.info("REST request to delete subject with ID: {}", id);
        boolean deleted = subjectService.deleteSubject(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<SubjectDTO>> getSubjectsByType(@PathVariable Integer typeId) {
        log.info("REST request to get subjects by type ID: {}", typeId);
        List<SubjectDTO> subjects = subjectService.getSubjectsByType(typeId);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/required")
    public ResponseEntity<List<SubjectDTO>> getRequiredSubjects() {
        log.info("REST request to get required subjects");
        List<SubjectDTO> subjects = subjectService.getRequiredSubjects();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/prerequisite/{code}")
    public ResponseEntity<SubjectDTO> getSubjectByPrerequisiteCode(@PathVariable String code) {
        log.info("REST request to get subject by prerequisite code: {}", code);
        SubjectDTO subject = subjectService.getSubjectByPrerequisiteCode(code);
        return subject != null
                ? ResponseEntity.ok(subject)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<SubjectDTO>> getSubjectsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) Boolean isRequired,
            @RequestParam(required = false) String keyword) {

        log.info("REST request to get paginated subjects with page: {}, size: {}", page, size);
        Page<SubjectDTO> subjectPage = subjectService.getSubjectsPaginated(
                page, size, sortBy, direction, isRequired, keyword);
        return ResponseEntity.ok(subjectPage);
    }
}
