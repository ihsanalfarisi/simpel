package com.burat.simpel.repository;

import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.model.ReviewAssessmentModel;
import com.burat.simpel.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.burat.simpel.dto.TrainingRecDTO;


import java.util.List;


public interface ReviewAssessmentDb extends JpaRepository<ReviewAssessmentModel,Long> {
    @Query("SELECT a FROM ReviewAssessmentModel a WHERE a.user = :user and a.event = :event")
    List<ReviewAssessmentModel> findReviewByUsername(@Param("user") UserModel user,
                                                     @Param("event") EventPeriodModel event);

    @Query("SELECT a FROM ReviewAssessmentModel a WHERE a.user = :user and a.competencyLevel = :competencyLevel and a.event = :event")
    List<ReviewAssessmentModel> findReviewByUsernameComp(@Param("user") UserModel user,
                                                         @Param("competencyLevel") CompetencyLevel competencyLevel,
                                                         @Param("event") EventPeriodModel event);

    @Query("SELECT new com.burat.simpel.dto.TrainingRecDTO(r.competencyLevel, count(r.competencyLevel)) FROM ReviewAssessmentModel r WHERE r.event = :eventPeriod and r.rerataGap < 0 GROUP BY r.competencyLevel order by count(r.competencyLevel) desc")
    List<TrainingRecDTO> getCompetencyCounts(@Param("eventPeriod") EventPeriodModel eventPeriod);

    List<ReviewAssessmentModel> findAllByCompetencyLevel_IdLevelAndEventAndRerataGapLessThan(Long idLevel, EventPeriodModel eventPeriod, Long zero);
}
