package com.burat.simpel.service;

import com.burat.simpel.model.DivisiModel;
import com.burat.simpel.repository.DivisiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DivisiServiceImpl implements DivisiService {
    @Autowired
    private DivisiDb divisiDb;

    @Override
    public List<DivisiModel> getAllDivisi() {
        return divisiDb.findAll();
    }

}
