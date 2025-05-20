package com.sgu.MHPL2.Service;

import com.sgu.MHPL2.DTO.SubjectTypeDTO;
import com.sgu.MHPL2.DTO.SubjectTypeMapper;
import com.sgu.MHPL2.Model.SubjectType;
import com.sgu.MHPL2.Repository.SubjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectTypeService {

    private final SubjectTypeRepository subjectTypeRepository;

    @Autowired
    public SubjectTypeService(SubjectTypeRepository subjectTypeRepository) {
        this.subjectTypeRepository = subjectTypeRepository;
    }

    public List<SubjectTypeDTO> getAllSubjectTypes() {
        List<SubjectType> subjectTypes = subjectTypeRepository.findAll();
        return subjectTypes.stream()
                .map(SubjectTypeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SubjectTypeDTO getSubjectTypeById(Integer id) {
        return subjectTypeRepository.findById(id)
                .map(SubjectTypeMapper::toDTO)
                .orElse(null);
    }

    public SubjectTypeDTO createSubjectType(SubjectTypeDTO subjectTypeDTO) {
        SubjectType subjectType = SubjectTypeMapper.toEntity(subjectTypeDTO);
        SubjectType savedSubjectType = subjectTypeRepository.save(subjectType);
        return SubjectTypeMapper.toDTO(savedSubjectType);
    }

    public SubjectTypeDTO updateSubjectType(Integer id, SubjectTypeDTO subjectTypeDTO) {
        if (subjectTypeRepository.existsById(id)) {
            subjectTypeDTO.setId(id);
            SubjectType subjectType = SubjectTypeMapper.toEntity(subjectTypeDTO);
            SubjectType updatedSubjectType = subjectTypeRepository.save(subjectType);
            return SubjectTypeMapper.toDTO(updatedSubjectType);
        }
        return null;
    }

    public boolean deleteSubjectType(Integer id) {
        if (subjectTypeRepository.existsById(id)) {
            subjectTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<SubjectTypeDTO> getSubjectTypesPaginated(int page, int size, String sortBy, String direction, String keyword) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SubjectType> subjectTypePage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Tìm kiếm với từ khóa (keyword) trong name hoặc description
            String searchKeyword = "%" + keyword.toLowerCase() + "%";
            subjectTypePage = subjectTypeRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    searchKeyword, searchKeyword, pageable);
        } else {
            // Không có từ khóa, lấy tất cả
            subjectTypePage = subjectTypeRepository.findAll(pageable);
        }

        return subjectTypePage.map(SubjectTypeMapper::toDTO);
    }
}
