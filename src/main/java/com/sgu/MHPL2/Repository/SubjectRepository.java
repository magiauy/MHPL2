package com.sgu.MHPL2.Repository;

import com.sgu.MHPL2.Model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Page<Subject> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Subject> findByIsRequired(Boolean isRequired, Pageable pageable);

    // Thêm phương thức mới để lấy danh sách (không phân trang)
    List<Subject> findByIsRequiredEquals(Boolean isRequired);

    Page<Subject> findByNameContainingIgnoreCaseAndIsRequired(String name, Boolean isRequired, Pageable pageable);

    List<Subject> findBySubjectTypeId(Integer subjectTypeId);

    Optional<Subject> findByPrerequisiteSubjectCode(String prerequisiteSubjectCode);
}
