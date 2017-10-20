package com.example.demo.usecase;

import com.example.demo.domain.InstrumentModel;
import com.example.demo.repository.InstrumentModelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import static java.lang.System.out;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by guilherme.lima on 19/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class OutputResultsTest {

    private InstrumentModelRepository instrumentModelRepository = mock(InstrumentModelRepository.class);
    private final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("dd-MMM-yyyy")
            .toFormatter()
            .withLocale(new Locale("Poland"));

    @Test
    public void outputingResults() {
        execute();
        when(instrumentModelRepository.getMeanByInstrumentNameAndDateBetween(any(String.class), any(LocalDate.class), any(LocalDate.class)))
            .thenReturn(new BigDecimal("3"));
        execute();
        when(instrumentModelRepository.getMeanByInstrumentName(any(String.class))).thenReturn(null);
        execute();
    }

    private void execute() {
        out.println(Optional.ofNullable(getMeanByInstrument("INSTRUMENT1"))
                .map(x -> x.setScale(2, RoundingMode.FLOOR).toString())
                .map("Mean for INSTRUMENT1: "::concat)
                .orElse("No values to calculate the mean for INSTRUMENT1."));
        out.println(Optional.ofNullable(getMeanByInstrumentAndPeriod("INSTRUMENT2", "Nov-2014"))
                .map(x -> x.setScale(2, RoundingMode.FLOOR).toString())
                .map("Mean of Nov-2014 for INSTRUMENT2: "::concat)
                .orElse("No values to calculate the mean of Nov-2014 for INSTRUMENT2."));
        out.println(Optional.ofNullable(getMaxValueByInstrument("INSTRUMENT3"))
                .map(x -> x.setScale(2, RoundingMode.FLOOR).toString())
                .map("Max for INSTRUMENT3: "::concat)
                .orElse("No values to calculate the max for INSTRUMENT3."));
        out.println(getSumOfTenNewests()
                .map(x -> x.setScale(2, RoundingMode.FLOOR).toString())
                .map("Sum of the newest 10 elements for INSTRUMENT4+: "::concat)
                .orElse("No values to calculate the sum of the 10 newest elements for INSTRUMENT4+."));
    }

    private BigDecimal getMeanByInstrumentAndPeriod(String instrumentName, String monthDashYear){
        LocalDate start = LocalDate.parse("01-" + monthDashYear, DATE_FORMAT);
        LocalDate end = LocalDate.parse(start.lengthOfMonth() + "-" + monthDashYear, DATE_FORMAT);
        return instrumentModelRepository.getMeanByInstrumentNameAndDateBetween(instrumentName, start, end);
    }

    private BigDecimal getMeanByInstrument(String instrumentName){
        return instrumentModelRepository.getMeanByInstrumentName(instrumentName);
    }

    private BigDecimal getMaxValueByInstrument(String instrumentName){
        return instrumentModelRepository.getMaxValueByInstrument(instrumentName);
    }

    private Optional<BigDecimal> getSumOfTenNewests(){
        return instrumentModelRepository
                .getSumOfTenNewestInstrumentNotIn(
                        Arrays.asList("INSTRUMENT1, INSTRUMENT2, INSTRUMENT3".split(",")),
                        new PageRequest(0, 10))
                .stream()
                .map(InstrumentModel::getValue)
                .reduce(BigDecimal::add);
    }
}
