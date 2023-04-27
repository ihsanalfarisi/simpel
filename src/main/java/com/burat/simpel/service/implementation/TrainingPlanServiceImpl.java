package com.burat.simpel.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.burat.simpel.model.TrainingPlanModel;
import com.burat.simpel.repository.TrainingPlanDb;
import com.burat.simpel.repository.UserDb;
import com.burat.simpel.service.TrainingPlanService;

@Service
public class TrainingPlanServiceImpl implements TrainingPlanService {
    @Autowired
    TrainingPlanDb trainingPlanDb;

    @Autowired
    UserDb userDb;

    @Override
    public List<TrainingPlanModel> getAllTrainingPlan() {
        return trainingPlanDb.findAll();
    }

    @Override
    public TrainingPlanModel getTrainingPlanById(Long id) {
        Optional<TrainingPlanModel> trainingPlan = trainingPlanDb.findById(id);
        if (trainingPlan.isPresent()) {
            return trainingPlan.get();
        } else
            return null;
    }

    @Override
    public void addTrainingPlan(TrainingPlanModel trainingPlanModel) {
        trainingPlanDb.save(trainingPlanModel);
    }

    @Override
    public void deleteTrainingPlan(TrainingPlanModel trainingPlanModel) {
        trainingPlanDb.delete(trainingPlanModel);
    }
}
