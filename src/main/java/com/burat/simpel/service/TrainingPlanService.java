package com.burat.simpel.service;

import java.util.List;

import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.model.UserModel;

public interface TrainingPlanService {
    List<TrainingPlanModel> getAllTrainingPlan();
    TrainingPlanModel getTrainingPlanById(Long id);
    public void addTrainingPlan(TrainingPlanModel trainingPlanModel);
    void deleteTrainingPlan(TrainingPlanModel trainingPlanModel);
}
