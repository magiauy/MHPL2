package com.sgu.MHPL2.Service;

import com.sgu.MHPL2.DTO.TeacherDTO;
import com.sgu.MHPL2.DTO.TeacherMapper;
import com.sgu.MHPL2.Model.Teacher;
import com.sgu.MHPL2.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Transactional
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        return teacherMapper.toDTO(teacherRepository.save(teacher));
    }

    @Transactional(readOnly = true)
    public TeacherDTO getTeacherById(Integer id) {
        return teacherRepository.findById(id)
                .map(teacherMapper::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TeacherDTO updateTeacher(Integer id, TeacherDTO teacherDTO) {
        return teacherRepository.findById(id)
                .map(existingTeacher -> {
                    // Update fields but preserve creation date
                    existingTeacher.setName(teacherDTO.getName());

                    // Handle position update through mapper
                    Teacher updated = teacherMapper.toEntity(teacherDTO);
                    existingTeacher.setPosition(updated.getPosition());

                    // Update status if provided
                    if (teacherDTO.getStatus() != null) {
                        existingTeacher.setStatus(teacherDTO.getStatus());
                    }

                    return teacherMapper.toDTO(teacherRepository.save(existingTeacher));
                })
                .orElse(null);
    }

    @Transactional
    public boolean deleteTeacher(Integer id) {
        return teacherRepository.findById(id)
                .map(teacher -> {
                    teacherRepository.delete(teacher);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public List<TeacherDTO> getTeachersByPosition(Integer positionId) {
        return teacherRepository.findByPositionId(positionId).stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TeacherDTO> getActiveTeachers() {
        return teacherRepository.findByStatusTrue().stream()
                .map(teacherMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Page<TeacherDTO> getTeachersPaginated(
            int page,
            int size,
            String sortBy,
            String direction,
            Boolean status,
            String keyword) {

        Sort sort = Sort.by(sortBy);
        if (direction.equalsIgnoreCase("DESC")) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Teacher> teachers;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Search with keyword
            if (status != null) {
                teachers = teacherRepository.findByNameContainingIgnoreCaseAndStatus(keyword, status, pageable);
            } else {
                teachers = teacherRepository.findByNameContainingIgnoreCase(keyword, pageable);
            }
        } else {
            // No keyword search
            if (status != null) {
                teachers = teacherRepository.findByStatus(status, pageable);
            } else {
                teachers = teacherRepository.findAll(pageable);
            }
        }

        return teachers.map(teacherMapper::toDTO);
    }
}