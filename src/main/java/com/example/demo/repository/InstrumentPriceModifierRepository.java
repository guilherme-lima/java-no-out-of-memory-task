package com.example.demo.repository;

import com.example.demo.domain.InstrumentPriceModifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by guilherme.lima on 17/10/2017.
 */
@Repository
public interface InstrumentPriceModifierRepository extends JpaRepository<InstrumentPriceModifier, Long> {

}
