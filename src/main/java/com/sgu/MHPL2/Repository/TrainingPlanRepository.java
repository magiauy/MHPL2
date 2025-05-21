package com.sgu.MHPL2.Repository;

import com.sgu.MHPL2.Model.TrainingPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Integer> {

    @Query("SELECT tp FROM TrainingPlan tp WHERE tp.academicYear = :academicYear")
    List<TrainingPlan> findByAcademicYear(@Param("academicYear") String academicYear);

    @Query("SELECT tp FROM TrainingPlan tp WHERE tp.semester = :semester")
    List<TrainingPlan> findBySemester(@Param("semester") Integer semester);

    @Query("SELECT tp FROM TrainingPlan tp WHERE tp.isActive = :isActive")
    List<TrainingPlan> findByIsActive(@Param("isActive") Boolean isActive);

    @Query("SELECT tp FROM TrainingPlan tp WHERE tp.department = :department")
    List<TrainingPlan> findByDepartment(@Param("department") String department);

    @Query("SELECT tp FROM TrainingPlan tp WHERE tp.status = :status")
    List<TrainingPlan> findByStatus(@Param("status") String status);

    @Query("SELECT tp FROM TrainingPlan tp WHERE " +
           "(:semester IS NULL OR tp.semester = :semester) AND " +
           "(:academicYear IS NULL OR tp.academicYear = :academicYear) AND " +
           "(:status IS NULL OR tp.status = :status)")
    Page<TrainingPlan> findByFilters(
            @Param("semester") Integer semester,
            @Param("academicYear") String academicYear,
            @Param("status") String status,
            Pageable pageable);

    @Query("SELECT tp FROM TrainingPlan tp WHERE LOWER(tp.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(tp.department) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<TrainingPlan> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT tp FROM TrainingPlan tp WHERE tp.isActive = true")
    List<TrainingPlan> findByIsActiveTrue();

    @Query("SELECT tp FROM TrainingPlan tp WHERE tp.academicYear = :academicYear AND tp.semester = :semester")
    List<TrainingPlan> findByAcademicYearAndSemester(
            @Param("academicYear") String academicYear,
            @Param("semester") Integer semester);
}
