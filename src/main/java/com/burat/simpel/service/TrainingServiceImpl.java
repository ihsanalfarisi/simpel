package com.burat.simpel.service;

import com.burat.simpel.model.CompetencyLevel;
import com.burat.simpel.model.TrainingModel;
import com.burat.simpel.repository.CompetencyLevelDb;
import com.burat.simpel.repository.TrainingDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService{
    @Autowired
    TrainingDb trainingDb;

    @Autowired
    CompetencyLevelDb competencyLevelDb;

    @Override
    public List<TrainingModel> getListTraining() {
        return trainingDb.findAll();
    }

    @Override
    public TrainingModel getTrainingById(Long trainingId) {
        return trainingDb.findByTrainingId(trainingId);
    }

    @Override
    public void addTraining(TrainingModel training) {
        trainingDb.save(training);
    }

    @Override
    public List<CompetencyLevel> processLevelOfTraining(List<CompetencyLevel> levelOfTraining) {
        List<CompetencyLevel> processedCompetencyLevels = new ArrayList<>();
        for (CompetencyLevel cl : levelOfTraining) {
            CompetencyLevel competencyLevel = competencyLevelDb.getCompetencyLevelByIdCompetencyAndLevel(cl.getIdCompetency(), cl.getLevel());
            processedCompetencyLevels.add(competencyLevel);
        }
        return processedCompetencyLevels;
    }

    @Override
    public void deleteTraining(TrainingModel training) {
        trainingDb.delete(training);
    }
}
