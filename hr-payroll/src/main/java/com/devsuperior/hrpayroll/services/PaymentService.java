package com.devsuperior.hrpayroll.services;

import com.devsuperior.hrpayroll.entities.Payment;
import com.devsuperior.hrpayroll.entities.Worker;
import com.devsuperior.hrpayroll.feignclients.WorkerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

// This is commented because we are using Feign
//    @Value("${hr-worker.host}")
//    private String workerHostUrl;

// This is commented because we are using Feign
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private WorkerFeignClient workerFeignClient;

    public Payment getPayment (Long workerId, int days) {

        //Using Feign
        Worker worker = workerFeignClient.findById(workerId).getBody();

        //For RestTemplate Calls
//        Map<String, String> uriVariables = new HashMap<>();
//        uriVariables.put("id", workerId.toString());
//        Worker worker = restTemplate.getForObject(workerHostUrl + "/workers/{id}", Worker.class, uriVariables);
        return new Payment(worker.getName(), worker.getDailyIncome(), days);
    }
}
