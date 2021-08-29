package com.devsuperior.hrpayroll.services;

import com.devsuperior.hrpayroll.entities.Payment;
import com.devsuperior.hrpayroll.entities.Worker;
import com.devsuperior.hrpayroll.feignclients.WorkerFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final WorkerFeignClient workerFeignClient;

    @Autowired
    public PaymentService(WorkerFeignClient workerFeignClient) {
        this.workerFeignClient = workerFeignClient;
    }

    public Payment getPayment(Long workerId, int days) {
        logger.info("Calling workerFeignClient for id: " + workerId);
        try {
            Worker worker = workerFeignClient.findById(workerId).getBody();
            return new Payment(worker.getName(), worker.getDailyIncome(), days);
        } catch (Exception e) {
            throw new IllegalStateException("Error calling work feign client", e);
        }
    }
}
