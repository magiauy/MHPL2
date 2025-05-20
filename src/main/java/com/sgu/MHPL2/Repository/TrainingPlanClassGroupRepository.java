package com.sgu.MHPL2.Repository;

import com.sgu.MHPL2.Model.TrainingPlanClassGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingPlanClassGroupRepository extends JpaRepository<TrainingPlanClassGroup, Integer> {

    @Query("SELECT tpcg FROM TrainingPlanClassGroup tpcg WHERE tpcg.trainingPlan.id = :trainingPlanId")
    List<TrainingPlanClassGroup> findByTrainingPlanId(@Param("trainingPlanId") Integer trainingPlanId);

    @Query("SELECT tpcg FROM TrainingPlanClassGroup tpcg WHERE tpcg.classGroup.id = :classGroupId")
    List<TrainingPlanClassGroup> findByClassGroupId(@Param("classGroupId") Integer classGroupId);

    @Query("SELECT tpcg FROM TrainingPlanClassGroup tpcg WHERE tpcg.trainingPlan.id = :trainingPlanId AND tpcg.classGroup.id = :classGroupId")
    TrainingPlanClassGroup findByTrainingPlanIdAndClassGroupId(
            @Param("trainingPlanId") Integer trainingPlanId,
            @Param("classGroupId") Integer classGroupId);

    @Modifying
    @Query("DELETE FROM TrainingPlanClassGroup tpcg WHERE tpcg.trainingPlan.id = :trainingPlanId")
    void deleteByTrainingPlanId(@Param("trainingPlanId") Integer trainingPlanId);

    @Modifying
    @Query("DELETE FROM TrainingPlanClassGroup tpcg WHERE tpcg.classGroup.id = :classGroupId")
    void deleteByClassGroupId(@Param("classGroupId") Integer classGroupId);
}
