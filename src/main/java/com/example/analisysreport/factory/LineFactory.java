package com.example.analisysreport.factory;

import com.example.analisysreport.enums.LineTypeEnum;
import com.example.analisysreport.exception.GenericException;
import com.example.analisysreport.strategy.ReadLine;
import com.example.analisysreport.strategy.ReadLineClient;
import com.example.analisysreport.strategy.ReadLineSale;
import com.example.analisysreport.strategy.ReadLineSalesMan;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LineFactory {

    private final ReadLineSalesMan readLineSalesMan;
    private final ReadLineClient readLineClient;
    private final ReadLineSale readLineSale;

    public ReadLine getDataLineProcessor(final String line) {

        final var lineTypeEnum = LineTypeEnum.fromLine(line)
                .orElseThrow(() -> new GenericException("Code not mapped: " + line));

        switch (lineTypeEnum) {
            case CLIENT:
                return readLineClient;
            case SALE:
                return readLineSale;
            case SALESMAN:
                return readLineSalesMan;
            default:
                throw new GenericException("Cannot found this enum code: " + line);
        }
    }
}
