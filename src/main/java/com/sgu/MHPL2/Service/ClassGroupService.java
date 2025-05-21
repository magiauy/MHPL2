package com.sgu.MHPL2.Service;

import com.sgu.MHPL2.DTO.ClassGroupDTO;
import com.sgu.MHPL2.DTO.ClassGroupMapper;
import com.sgu.MHPL2.DTO.TeacherAssignmentDTO;
import com.sgu.MHPL2.DTO.TeacherAssignmentMapper;
import com.sgu.MHPL2.Model.ClassGroup;
import com.sgu.MHPL2.Model.TeacherAssignment;
import com.sgu.MHPL2.Repository.ClassGroupRepository;
import com.sgu.MHPL2.Repository.TeacherAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassGroupService {

    @Autowired
    private ClassGroupRepository classGroupRepository;

    @Autowired
    private TeacherAssignmentRepository teacherAssignmentRepository;

    @Autowired
    private ClassGroupMapper classGroupMapper;

    @Autowired
    private TeacherAssignmentMapper teacherAssignmentMapper;

    public List<ClassGroupDTO> getAllClassGroups() {
        return classGroupRepository.findAll().stream()
                .map(classGroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<ClassGroupDTO> getClassGroupsByFilters(Integer semester, String schoolYear, String status, Pageable pageable) {
        return classGroupRepository.findByFilters(semester, schoolYear, status, pageable)
                .map(classGroupMapper::toDTO);
    }

    public Page<ClassGroupDTO> searchClassGroups(String keyword, Pageable pageable) {
        return classGroupRepository.searchByKeyword(keyword, pageable)
                .map(classGroupMapper::toDTO);
    }

    public ClassGroupDTO getClassGroupById(Integer id) {
        Optional<ClassGroup> classGroupOptional = classGroupRepository.findById(id);
        return classGroupOptional.map(classGroupMapper::toDTO).orElse(null);
    }

    public List<ClassGroupDTO> getClassGroupsByTrainingPlanId(Integer trainingPlanId) {
        List<ClassGroup> classGroups = classGroupRepository.findByTrainingPlanId(trainingPlanId);
        return classGroups.stream()
                .map(classGroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClassGroupDTO createClassGroup(ClassGroupDTO classGroupDTO) {
        // Set default values if needed
        if (classGroupDTO.getStatus() == null) {
            classGroupDTO.setStatus("PLANNED");
        }

        // Convert DTO to entity and save
        ClassGroup classGroup = classGroupMapper.toEntity(classGroupDTO);
        classGroup.setCreatedAt(LocalDateTime.now());
        classGroup.setUpdatedAt(LocalDateTime.now());

        // Save the class group
        ClassGroup savedClassGroup = classGroupRepository.save(classGroup);

        // Process teacher assignments if any
        if (classGroupDTO.getTeacherAssignments() != null && !classGroupDTO.getTeacherAssignments().isEmpty()) {
            saveTeacherAssignments(savedClassGroup.getId(), classGroupDTO.getTeacherAssignments());
        }

        // Return the saved class group with all assignments
        return getClassGroupById(savedClassGroup.getId());
    }

    @Transactional
    public ClassGroupDTO updateClassGroup(Integer id, ClassGroupDTO classGroupDTO) {
        if (!classGroupRepository.existsById(id)) {
            return null;
        }

        ClassGroup classGroup = classGroupMapper.toEntity(classGroupDTO);
        classGroup.setId(id);
        classGroup.setUpdatedAt(LocalDateTime.now());

        // Save the class group
        ClassGroup updatedClassGroup = classGroupRepository.save(classGroup);

        // Update teacher assignments
        if (classGroupDTO.getTeacherAssignments() != null) {
            // Remove existing assignments
            teacherAssignmentRepository.deleteByClassGroupId(id);

            // Save new assignments
            saveTeacherAssignments(id, classGroupDTO.getTeacherAssignments());
        }

        // Return the updated class group with all assignments
        return getClassGroupById(updatedClassGroup.getId());
    }

    @Transactional
    public boolean deleteClassGroup(Integer id) {
        if (!classGroupRepository.existsById(id)) {
            return false;
        }

        // Delete teacher assignments first
        teacherAssignmentRepository.deleteByClassGroupId(id);

        // Delete the class group
        classGroupRepository.deleteById(id);
        return true;
    }

    public List<ClassGroupDTO> getClassGroupsBySubjectId(Integer subjectId) {
        return classGroupRepository.findBySubjectId(subjectId).stream()
                .map(classGroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ClassGroupDTO> getClassGroupsByTeacherId(Integer teacherId) {
        return classGroupRepository.findByTeacherId(teacherId).stream()
                .map(classGroupMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Helper method to save teacher assignments
    private void saveTeacherAssignments(Integer classGroupId, List<TeacherAssignmentDTO> assignmentDTOs) {
        for (TeacherAssignmentDTO assignmentDTO : assignmentDTOs) {
            // Set the class group ID
            assignmentDTO.setClassGroupId(classGroupId);

            // Convert DTO to entity
            TeacherAssignment assignment = teacherAssignmentMapper.toEntity(assignmentDTO);

            // Set creation/update timestamps
            assignment.setCreatedAt(LocalDateTime.now());
            assignment.setUpdatedAt(LocalDateTime.now());

            // Set default status if not provided
            if (assignment.getStatus() == null) {
                assignment.setStatus(true);
            }

            // Save the assignment
            teacherAssignmentRepository.save(assignment);
        }
    }

    @Transactional
    public ClassGroupDTO addTeacherAssignment(TeacherAssignmentDTO assignmentDTO) {
        // Check if the class group exists
        if (!classGroupRepository.existsById(assignmentDTO.getClassGroupId())) {
            return null;
        }

        // Convert DTO to entity
        TeacherAssignment assignment = teacherAssignmentMapper.toEntity(assignmentDTO);

        // Set creation/update timestamps
        assignment.setCreatedAt(LocalDateTime.now());
        assignment.setUpdatedAt(LocalDateTime.now());

        // Set default status if not provided
        if (assignment.getStatus() == null) {
            assignment.setStatus(true);
        }

        // Save the assignment
        teacherAssignmentRepository.save(assignment);

        // Return the updated class group
        return getClassGroupById(assignmentDTO.getClassGroupId());
    }

    @Transactional
    public ClassGroupDTO updateTeacherAssignment(TeacherAssignmentDTO assignmentDTO) {
        // Check if the assignment exists
        Optional<TeacherAssignment> existingAssignment = teacherAssignmentRepository.findById(assignmentDTO.getId());
        if (existingAssignment.isEmpty() || !existingAssignment.get().getClassGroupId().equals(assignmentDTO.getClassGroupId())) {
            return null;
        }

        // Convert DTO to entity
        TeacherAssignment assignment = teacherAssignmentMapper.toEntity(assignmentDTO);

        // Update timestamp
        assignment.setUpdatedAt(LocalDateTime.now());

        // Preserve creation timestamp
        assignment.setCreatedAt(existingAssignment.get().getCreatedAt());

        // Save the updated assignment
        teacherAssignmentRepository.save(assignment);

        // Return the updated class group
        return getClassGroupById(assignmentDTO.getClassGroupId());
    }

    @Transactional
    public boolean removeTeacherAssignment(Integer classGroupId, Integer assignmentId) {
        // Check if the assignment exists and belongs to the specified class group
        Optional<TeacherAssignment> assignment = teacherAssignmentRepository.findById(assignmentId);
        if (assignment.isEmpty() || !assignment.get().getClassGroupId().equals(classGroupId)) {
            return false;
        }

        // Delete the assignment
        teacherAssignmentRepository.deleteById(assignmentId);
        return true;
    }
}
