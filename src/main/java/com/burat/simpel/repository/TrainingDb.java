package com.burat.simpel.repository;

import com.burat.simpel.model.TrainingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingDb extends JpaRepository<TrainingModel, Long> {
    TrainingModel findByTrainingId(Long trainingId);
}
