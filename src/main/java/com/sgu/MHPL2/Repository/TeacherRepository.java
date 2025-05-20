package com.sgu.MHPL2.Repository;

import com.sgu.MHPL2.Model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    List<Teacher> findByPositionId(Integer positionId);
    List<Teacher> findByStatusTrue();
    Page<Teacher> findByStatus(Boolean status, Pageable pageable);
    Page<Teacher> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Teacher> findByNameContainingIgnoreCaseAndStatus(String keyword, Boolean status, Pageable pageable);
}
