package com.example.analisysreport.strategy;

import com.example.analisysreport.enums.LineTypeEnum;
import com.example.analisysreport.exception.GenericException;
import com.example.analisysreport.vo.ReportVO;
import java.util.regex.Pattern;

public interface ReadLine {

    ReportVO readFileLine(final ReportVO fileReport, final String line);

    default void validate(final String line) {

        final var lineTypeEnum = LineTypeEnum.fromLine(line)
                .orElseThrow(() -> new GenericException("Code not mapped: " + line));

        if (!Pattern.compile(lineTypeEnum.getRegex()).matcher(line).find()) {
            throw new GenericException("Invalid file line: " + line);
        }
    }
}

