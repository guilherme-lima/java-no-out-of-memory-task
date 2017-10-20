package com.example.demo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Created by guilherme.lima on 17/10/2017.
 */
@Data
@Table
@Entity
public class InstrumentModel {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String instrumentName;

    @Getter
    @Setter
    private LocalDate date;

    @Getter
    @Setter
    private BigDecimal value;
}
