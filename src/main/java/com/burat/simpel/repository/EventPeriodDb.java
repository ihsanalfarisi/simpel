package com.burat.simpel.repository;

import com.burat.simpel.model.EventPeriodModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventPeriodDb extends JpaRepository<EventPeriodModel,Long> {
    EventPeriodModel findByPeriodName(String name);
    List<EventPeriodModel> findAllByJenis(String jenis);

    EventPeriodModel findByIdEventPriod(Long idEventPriod);
}
