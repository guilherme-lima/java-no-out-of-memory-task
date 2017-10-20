package com.example.demo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by guilherme.lima on 17/10/2017.
 */
@Table(name = "INSTRUMENT_PRICE_MODIFIER")
@Data
@Entity
public class InstrumentPriceModifier {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MULTIPLIER")
    private Double multiplier;
}
