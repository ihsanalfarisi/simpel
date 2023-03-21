package com.burat.simpel.repository;

import com.burat.simpel.model.Competency;
import com.burat.simpel.model.CompetencyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetencyLevelDb extends JpaRepository<CompetencyLevel, Long> {
    CompetencyLevel getCompetencyLevelByIdCompetencyAndLevel(Competency idCompetency, Integer level);
}
