package com.burat.simpel.service;

import com.burat.simpel.model.EventPeriodModel;

import java.util.List;

public interface EventPeriodService {
    List<EventPeriodModel> getAllEventPeriod();
    EventPeriodModel getByEventPeriodName(String name);
}
