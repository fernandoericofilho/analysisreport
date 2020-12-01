package com.example.analisysreport.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LineTypeEnum {

    SALESMAN("001", "^(001)\\ç(\\d+)\\ç([ A-Za-z]+)\\ç(\\d+\\.*\\d*)$"),
    CLIENT("002", "^(002)\\ç(\\d+)\\ç([ A-Za-z]+)\\ç([ A-Za-z]+)$"),
    SALE("003", "^(003)\\ç(\\d+)\\ç\\[(.+)\\]\\ç([ A-Za-z]+)$");

    private static final String REGEX_VALIDATOR = "^(\\d*)\\ç(.*)$";

    private final String code;
    private final String regex;

    public static Optional<LineTypeEnum> fromLine(final String line) {
        Matcher saleItemMatcher = Pattern.compile(REGEX_VALIDATOR).matcher(line);
        if (saleItemMatcher.find()) {
            final var code = saleItemMatcher.group(1);
            return Arrays.stream(values())
                    .filter(c -> c.code.equals(code))
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }
}
