package com.devsuperior.hrworker.controllers;

import com.devsuperior.hrworker.services.WorkerService;
import com.devsuperior.hrworker.entities.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/workers")
public class WorkerController {

    private static Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private Environment env;

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public ResponseEntity<List<Worker>> findAll(){
        List<Worker> list = workerService.findAllWorkers();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Worker> findById(@PathVariable Long id){

        logger.info("PORT = " + env.getProperty("local.server.port"));
        logger.info("Calling findWorkerById on workerService for id: " + id);
        Worker obj = workerService.findWorkerById(id);
        return ResponseEntity.ok(obj);
    }
}
