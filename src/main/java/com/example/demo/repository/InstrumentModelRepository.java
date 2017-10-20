package com.example.demo.repository;

import com.example.demo.domain.InstrumentModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by guilherme.lima on 17/10/2017.
 */
@Repository
public interface InstrumentModelRepository extends JpaRepository<InstrumentModel, Long> {

    List<InstrumentModel> findAllByInstrumentNameIn(List<String> instrumentNames);

    @Query("select avg(i.value) from InstrumentModel i where i.instrumentName = ?1")
    BigDecimal getMeanByInstrumentName(String instrumentName);

    @Query("select avg(i.value) from InstrumentModel i where i.instrumentName = ?1 and i.date between ?2 and ?3")
    BigDecimal getMeanByInstrumentNameAndDateBetween(String instrumentName, LocalDate start, LocalDate end);

    @Query("select max(i.value) from InstrumentModel i where i.instrumentName = ?1")
    BigDecimal getMaxValueByInstrument(String instrumentName);

    @Query("select i from InstrumentModel i where i.instrumentName not in (?1) order by i.date desc")
    List<InstrumentModel> getSumOfTenNewestInstrumentNotIn(List<String> instrumentsName, Pageable page);
}
