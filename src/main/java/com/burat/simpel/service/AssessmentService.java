package com.burat.simpel.service;

import com.burat.simpel.model.AssessmentModel;

public interface AssessmentService {
    void add(AssessmentModel assessmentModel);

    Boolean checkDuplicate(AssessmentModel assessmentModel);
}
