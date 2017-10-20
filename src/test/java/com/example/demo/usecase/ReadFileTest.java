package com.example.demo.usecase;

import com.example.demo.domain.InstrumentModel;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by guilherme.lima on 19/10/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ReadFileTest {

    @Autowired
    private ReadFile readFile;

    private final DateTimeFormatter DATE_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("dd-MMM-yyyy")
            .toFormatter()
            .withLocale(new Locale("Poland"));

    @Test
    public void extractingInfoFromLinesOfFile() throws Exception {

        InstrumentModel inst = new InstrumentModel();
        inst.setInstrumentName("INSTRUMENT1");
        inst.setDate(LocalDate.parse("04-Nov-2014", DATE_FORMAT));
        inst.setValue(new BigDecimal("3.14"));
        assertEquals(readFile.extractLineInfos("INSTRUMENT1,04-Nov-2014,3.14"), inst);
        assertNotEquals(readFile.extractLineInfos("INSTRUMENT1,04-Nov-2014,3.15"), inst);
        assertNull(readFile.extractLineInfos("1"));
        assertNull(readFile.extractLineInfos("1,XXX,A.BC"));
        assertNull(readFile.extractLineInfos(null));

        Assertions.assertThat(readFile.extractLineInfos("INSTRUMENT1,04-Nov-2014,3.14")).isInstanceOf(InstrumentModel.class);
        Assertions.assertThat(readFile.extractLineInfos("INSTRUMENT2,32-Dec-2013,2.12")).isNull();
        Assertions.assertThat(readFile.extractLineInfos("INSTRUMENT2,01-Jan-2015,2.00")).isNull();
        Assertions.assertThat(readFile.extractLineInfos("INSTRUMENT2,01-Jan-2015,9999999999999999999999999999999999999999999999999999999999999")).isNull();
        Assertions.assertThat(readFile.extractLineInfos("INSTRUMENT2,01-Jan-2010,9999999999999999999999999999999999999999999999999999999999999")).isInstanceOf(InstrumentModel.class);
        Assertions.assertThat(readFile.extractLineInfos("INSTRUMENT2,01-Jan-2010,.9999999999999999999999999999999999999999999999999999999999999")).isInstanceOf(InstrumentModel.class);
    }
}
