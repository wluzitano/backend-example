package com.devsuperior.hrpayroll.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;


@Data
public class Worker implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long Id;
    private String name;
    private Double dailyIncome;
}
