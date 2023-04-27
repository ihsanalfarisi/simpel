package com.burat.simpel.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.model.TrainingPlanModel;

public interface TrainingPlanDb extends JpaRepository<TrainingPlanModel, Long>{
}
