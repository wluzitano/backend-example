package com.devsuperior.hrworker.controllers;

import com.devsuperior.hrworker.entities.Worker;
import com.devsuperior.hrworker.services.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${test.config}")
	private String testConfig;

    @Autowired
    private Environment env;

    @Autowired
    private WorkerService workerService;

	@GetMapping(value="/configs")
	public ResponseEntity<Void> getConfigs() {
		logger.info("Config = ".concat(testConfig));
		return ResponseEntity.noContent().build();
	}

    @GetMapping
    public ResponseEntity<List<Worker>> findAll() {
        List<Worker> list = workerService.findAllWorkers();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Worker> findById(@PathVariable Long id) {
        //    Testando o metodo alternativo, forcando excecao para o hystrix ser chamado
        //        int x = 1;
        //        if (x == 1) {
        //            throw new RuntimeException("Runtime exception test");
        //        }

//        O ribbon tem 1 seg, se eu colocar 3 vai estourar e ir pro alternativo, no postman, vai mostrar + de 1000 ms, que quer dizer que
//        o timeout do ribbon foi estourado e retornou o metodo alternativo quando esse get eh chamado.
//                try {
//                    Thread.sleep(3000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

        logger.info("PORT = " + env.getProperty("local.server.port"));
        logger.info("Calling findWorkerById on workerService for id: " + id);
        Worker obj = workerService.findWorkerById(id);
        return ResponseEntity.ok(obj);
    }
}
