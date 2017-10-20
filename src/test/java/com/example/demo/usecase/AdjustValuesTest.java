package com.example.demo.usecase;

import com.example.demo.domain.InstrumentModel;
import com.example.demo.domain.InstrumentPriceModifier;
import com.example.demo.repository.InstrumentModelRepository;
import com.example.demo.repository.InstrumentPriceModifierRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by guilherme.lima on 19/10/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AdjustValuesTest {

    @Mock
    private InstrumentModelRepository instrumentModelRepository;
    @Mock
    private InstrumentPriceModifierRepository instrumentPriceModifierRepository;

    private final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("dd-MMM-yyyy")
            .toFormatter()
            .withLocale(new Locale("Poland"));

    @Before
    public void setup() {
        InstrumentModel inst = new InstrumentModel();
        inst.setId(1L);
        inst.setInstrumentName("INSTRUMENT1");
        inst.setDate(LocalDate.parse("01-Feb-2000", DATE_FORMAT));
        inst.setValue(new BigDecimal("2.12"));

        InstrumentPriceModifier instMod = new InstrumentPriceModifier();
        instMod.setId(1L);
        instMod.setName("INSTRUMENT1");
        instMod.setMultiplier(new Double("5"));

        when(instrumentModelRepository.findAllByInstrumentNameIn(any(List.class))).thenReturn(Arrays.asList(inst));
        when(instrumentPriceModifierRepository.findAll()).thenReturn(Arrays.asList(instMod));
    }

    @Test
    public void perfectScenario() throws Exception {
        List<InstrumentPriceModifier> priceModifiers = instrumentPriceModifierRepository.findAll();
        getInstrumentsToBeAdjusted(priceModifiers).forEach(instrument -> {
            Optional<InstrumentPriceModifier> modifier = priceModifiers
                    .stream()
                    .filter(p -> p.getName().equals(instrument.getInstrumentName()))
                    .findFirst();
            modifier.ifPresent(x -> instrument
                    .setValue(
                            new BigDecimal(x.getMultiplier()).multiply(instrument.getValue())
                    ));
            instrumentModelRepository.save(instrument);
        });
    }

    private List<InstrumentModel> getInstrumentsToBeAdjusted(List<InstrumentPriceModifier> priceModifiers) throws Exception {
        return instrumentModelRepository
                .findAllByInstrumentNameIn(priceModifiers.stream()
                        .map(InstrumentPriceModifier::getName)
                        .collect(Collectors.toList()));
    }
}
