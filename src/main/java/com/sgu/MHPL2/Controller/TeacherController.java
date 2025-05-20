package com.sgu.MHPL2.Controller;

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
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        log.info("REST request to create teacher with name: {}", teacherDTO.getName());
        TeacherDTO createdTeacher = teacherService.createTeacher(teacherDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable("id") Integer id) {
        log.info("REST request to get teacher by ID: {}", id);
        TeacherDTO teacher = teacherService.getTeacherById(id);
        return teacher != null
                ? ResponseEntity.ok(teacher)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        log.info("REST request to get all teachers");
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<TeacherDTO>> getTeachersByPosition(@PathVariable Integer positionId) {
        log.info("REST request to get teachers by position ID: {}", positionId);
        return ResponseEntity.ok(teacherService.getTeachersByPosition(positionId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<TeacherDTO>> getActiveTeachers() {
        log.info("REST request to get active teachers");
        return ResponseEntity.ok(teacherService.getActiveTeachers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable("id") Integer id, @RequestBody TeacherDTO teacherDTO) {
        log.info("REST request to update teacher with ID: {}", id);
        TeacherDTO updatedTeacher = teacherService.updateTeacher(id, teacherDTO);
        return updatedTeacher != null
                ? ResponseEntity.ok(updatedTeacher)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") Integer id) {
        log.info("REST request to delete teacher with ID: {}", id);
        boolean deleted = teacherService.deleteTeacher(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<TeacherDTO>> getTeachersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String keyword) {

        log.info("REST request to get paginated teachers with page: {}, size: {}", page, size);
        Page<TeacherDTO> teacherPage = teacherService.getTeachersPaginated(page, size, sortBy, direction, status, keyword);
        return ResponseEntity.ok(teacherPage);
    }
}
