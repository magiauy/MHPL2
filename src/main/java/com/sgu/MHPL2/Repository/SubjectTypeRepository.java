package com.sgu.MHPL2.Repository;

import com.sgu.MHPL2.Model.SubjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectTypeRepository extends JpaRepository<SubjectType, Integer> {

    @Query("SELECT st FROM SubjectType st WHERE LOWER(st.name) LIKE :name OR LOWER(st.description) LIKE :description")
    Page<SubjectType> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            @Param("name") String name,
            @Param("description") String description,
            Pageable pageable);
}
