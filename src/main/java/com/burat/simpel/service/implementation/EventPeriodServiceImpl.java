package com.burat.simpel.service.implementation;

import com.burat.simpel.model.EventPeriodModel;
import com.burat.simpel.repository.EventPeriodDb;
import com.burat.simpel.service.EventPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventPeriodServiceImpl implements EventPeriodService {
    @Autowired
    EventPeriodDb eventPeriodDb;

    @Override
    public EventPeriodModel getByEventPeriodName(String name) {
        return eventPeriodDb.findByPeriodName(name);
    }

    @Override
    public List<EventPeriodModel> getAllEventPeriod() {
        return eventPeriodDb.findAll();
    }
}
