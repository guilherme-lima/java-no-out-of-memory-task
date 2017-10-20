package com.example.demo.usecase;

import com.example.demo.domain.InstrumentModel;
import com.example.demo.domain.InstrumentPriceModifier;
import com.example.demo.repository.InstrumentModelRepository;
import com.example.demo.repository.InstrumentPriceModifierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by guilherme.lima on 19/10/2017.
 */
@Service
@AllArgsConstructor
public class AdjustValues {

    private InstrumentModelRepository instrumentModelRepository;
    private InstrumentPriceModifierRepository instrumentPriceModifierRepository;

    public void execute() {
        try {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<InstrumentModel> getInstrumentsToBeAdjusted(List<InstrumentPriceModifier> priceModifiers) throws Exception {
        return instrumentModelRepository
                .findAllByInstrumentNameIn(priceModifiers.stream()
                        .map(InstrumentPriceModifier::getName)
                        .collect(Collectors.toList()));
    }
}
