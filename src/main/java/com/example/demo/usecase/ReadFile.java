package com.example.demo.usecase;

import com.example.demo.domain.InstrumentModel;
import com.example.demo.repository.InstrumentModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by guilherme.lima on 19/10/2017.
 */
@Service
@AllArgsConstructor
public class ReadFile {

    private InstrumentModelRepository instrumentModelRepository;

    private final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("dd-MMM-yyyy")
            .toFormatter()
            .withLocale(new Locale("Poland"));
    private final LocalDate TODAY = LocalDate.of(2014, 12, 19);

    public void execute() {
        try (Stream<String> stream = Files.lines(Paths.get("file_example.txt"))) {
            stream.map(this::extractLineInfos).filter(Objects::nonNull).forEach(instrumentModelRepository::save);
//            Use "parallel()" method alternatively to gain performance, but spending more memory
//            stream.parallel().map(this::extractLineInfos).filter(Objects::nonNull).forEach(instrumentModelRepository::save);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public InstrumentModel extractLineInfos(String line) {
        try {
            String[] lineSplited = line.split(",");
            lineSplited = Arrays.stream(lineSplited).filter(x -> !Objects.isNull(x)).toArray(String[]::new);
            InstrumentModel instrumentModel = new InstrumentModel();
            instrumentModel.setInstrumentName(lineSplited[0]);
            instrumentModel.setDate(LocalDate.parse(lineSplited[1], DATE_FORMAT));
            instrumentModel.setValue(new BigDecimal(lineSplited[2]));
            return TODAY.isAfter(instrumentModel.getDate()) ? instrumentModel : null;
        } catch (DateTimeParseException | NumberFormatException | NullPointerException | ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }
}
