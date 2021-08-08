package com.devsuperior.hruser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@SpringBootApplication
public class HrUserApplication implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(HrUserApplication.class, args);
    }

    //So usado para gerar um passsword encriptado pra se quiser salvar no banco
    @Override
    public void run(String... args) throws Exception {
        System.out.println("BCRYPT = " + passwordEncoder.encode("123456"));
    }
}
