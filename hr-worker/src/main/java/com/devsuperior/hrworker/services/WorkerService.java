package com.devsuperior.hrworker.services;

import com.devsuperior.hrworker.entities.Worker;
import com.devsuperior.hrworker.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository repository;

    public List<Worker> findAllWorkers(){
        List<Worker> listofWorkers = repository.findAll();
        return listofWorkers;
    }

    public Worker findWorkerById(Long id){
        Worker worker = repository.findById(id).get();
        return worker;
    }
}
