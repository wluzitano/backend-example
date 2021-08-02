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
@RequestMappi<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.4.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.devsuperior</groupId>
	<artifactId>hr-config-server</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>hr-config-server</name>
	<description>Config Server</description>

	<properties>
		<java.version>11</java.version>
		<spring-cloud.version>Hoxton.SR8</spring-cloud.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>ng(value = "/workers")
public class WorkerController {

    private static Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private Environment env;

    @Autowired
    private WorkerService workerService;

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
