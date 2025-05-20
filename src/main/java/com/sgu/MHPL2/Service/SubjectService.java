package com.sgu.MHPL2.Service;

import com.sgu.MHPL2.DTO.SubjectDTO;
import com.sgu.MHPL2.DTO.SubjectMapper;
import com.sgu.MHPL2.Model.Subject;
import com.sgu.MHPL2.Repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Transactional
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        log.info("Creating new subject with name: {}", subjectDTO.getName());
        Subject subject = subjectMapper.toEntity(subjectDTO);
        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.toDTO(savedSubject);
    }

    @Transactional(readOnly = true)
    public SubjectDTO getSubjectById(Integer id) {
        log.info("Fetching subject with ID: {}", id);
        return subjectRepository.findById(id)
                .map(subjectMapper::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<SubjectDTO> getAllSubjects() {
        log.info("Fetching all subjects");
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubjectDTO updateSubject(Integer id, SubjectDTO subjectDTO) {
        log.info("Updating subject with ID: {}", id);
        return subjectRepository.findById(id)
                .map(existingSubject -> {
                    // Cập nhật các trường
                    existingSubject.setName(subjectDTO.getName());
                    existingSubject.setCredits(subjectDTO.getCredits());
                    existingSubject.setTheoryLessons(subjectDTO.getTheoryLessons());
                    existingSubject.setPracticeLessons(subjectDTO.getPracticeLessons());
                    existingSubject.setTrainingLessons(subjectDTO.getTrainingLessons());
                    existingSubject.setIsRequired(subjectDTO.getIsRequired());
                    existingSubject.setSubjectTypeId(subjectDTO.getSubjectTypeId());
                    existingSubject.setCoefficient(subjectDTO.getCoefficient());
                    existingSubject.setPrerequisiteSubjectCode(subjectDTO.getPrerequisiteSubjectCode());

                    return subjectMapper.toDTO(subjectRepository.save(existingSubject));
                })
                .orElse(null);
    }

    @Transactional
    public boolean deleteSubject(Integer id) {
        log.info("Deleting subject with ID: {}", id);
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<SubjectDTO> getSubjectsByType(Integer typeId) {
        log.info("Fetching subjects with type ID: {}", typeId);
        return subjectRepository.findBySubjectTypeId(typeId).stream()
                .map(subjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SubjectDTO> getRequiredSubjects() {
        log.info("Fetching all required subjects");
        return subjectRepository.findByIsRequiredEquals(true).stream()
                .map(subjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubjectDTO getSubjectByPrerequisiteCode(String code) {
        log.info("Fetching subject with prerequisite code: {}", code);
        return subjectRepository.findByPrerequisiteSubjectCode(code)
                .map(subjectMapper::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> getSubjectsPaginated(
            int page,
            int size,
            String sortBy,
            String direction,
            Boolean isRequired,
            String keyword) {

        log.info("Fetching paginated subjects - page: {}, size: {}", page, size);
        Sort sort = Sort.by(sortBy);
        if (direction.equalsIgnoreCase("DESC")) {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Subject> subjects;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Tìm kiếm với từ khóa
            if (isRequired != null) {
                subjects = subjectRepository.findByNameContainingIgnoreCaseAndIsRequired(keyword, isRequired, pageable);
            } else {
                subjects = subjectRepository.findByNameContainingIgnoreCase(keyword, pageable);
            }
        } else {
            // Không tìm kiếm từ khóa
            if (isRequired != null) {
                subjects = subjectRepository.findByIsRequired(isRequired, pageable);
            } else {
                subjects = subjectRepository.findAll(pageable);
            }
        }

        return subjects.map(subjectMapper::toDTO);
    }
}
