package com.burat.simpel.service.implementation;

import com.burat.simpel.model.AssessmentModel;
import com.burat.simpel.repository.AssessmentDb;
import com.burat.simpel.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentServiceImpl implements AssessmentService {
    @Autowired
    AssessmentDb assessmentDb;

    @Override
    public void add(AssessmentModel assessmentModel) {
        assessmentDb.save(assessmentModel);
    }

    @Override
    public Boolean checkDuplicate(AssessmentModel assessmentModel) {
        return assessmentDb.checkDuplicateByUserAssessorEvent(
                assessmentModel.getAssessor().getAccountUuid(),
                assessmentModel.getUser().getAccountUuid(),
                assessmentModel.getEvent().getIdEventPriod()
                ).isPresent();
    }
}
