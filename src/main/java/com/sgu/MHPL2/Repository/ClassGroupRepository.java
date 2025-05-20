package com.sgu.MHPL2.Repository;

import com.sgu.MHPL2.Model.ClassGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassGroupRepository extends JpaRepository<ClassGroup, Integer> {

    @Query("SELECT cg FROM ClassGroup cg WHERE cg.subjectId = :subjectId")
    List<ClassGroup> findBySubjectId(@Param("subjectId") Integer subjectId);

    @Query("SELECT cg FROM ClassGroup cg WHERE cg.teacherId = :teacherId")
    List<ClassGroup> findByTeacherId(@Param("teacherId") Integer teacherId);

    @Query("SELECT cg FROM ClassGroup cg WHERE " +
           "(:semester IS NULL OR cg.semester = :semester) AND " +
           "(:schoolYear IS NULL OR cg.schoolYear = :schoolYear) AND " +
           "(:status IS NULL OR cg.status = :status)")
    Page<ClassGroup> findByFilters(
            @Param("semester") Integer semester,
            @Param("schoolYear") String schoolYear,
            @Param("status") String status,
            Pageable pageable);

    @Query("SELECT cg FROM ClassGroup cg WHERE " +
           "LOWER(cg.groupCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(cg.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ClassGroup> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
