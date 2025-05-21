package com.sgu.MHPL2.Repository;

import com.sgu.MHPL2.Model.TeacherAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment, Integer> {
    List<TeacherAssignment> findByClassGroupId(Integer classGroupId);
    void deleteByClassGroupId(Integer classGroupId);
}
