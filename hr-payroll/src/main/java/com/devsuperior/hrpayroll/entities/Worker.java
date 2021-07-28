package com.devsuperior.hrpayroll.entities;

import java.io.Serializable;
import java.util.Objects;


public class Worker implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long Id;
    private String name;
    private Double dailyIncome;

    public Worker(){
    }

    public Worker(Long id, String name, Double dailyIncome) {
        Id = id;
        this.name = name;
        this.dailyIncome = dailyIncome;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(Double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return Id.equals(worker.Id) && name.equals(worker.name) && dailyIncome.equals(worker.dailyIncome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, name, dailyIncome);
    }
}
