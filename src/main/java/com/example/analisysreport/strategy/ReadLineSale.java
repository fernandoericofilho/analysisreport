package com.example.analisysreport.strategy;

import com.example.analisysreport.enums.LineTypeEnum;
import com.example.analisysreport.exception.GenericException;
import com.example.analisysreport.vo.ReportVO;
import com.example.analisysreport.vo.SaleItemVO;
import com.example.analisysreport.vo.SaleVO;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReadLineSale implements ReadLine {

    private final static String SALE_REGEX_PATTERN = "([^,]+)";
    private final static String SALE_ITEM_REGEX_PATTERN = "^(\\d+)\\-(\\d+)\\-(\\d+\\.*\\d*)$";

    @Override
    public ReportVO readFileLine(ReportVO fileReport, String line) {

        Matcher saleLineMatcher = Pattern.compile(LineTypeEnum.SALE.getRegex()).matcher(line);

        if (!saleLineMatcher.find()) {
            throw new GenericException("Invalid file line -> " + line);
        }

        final var sale = SaleVO.builder()
                .id(Long.valueOf(saleLineMatcher.group(2)))
                .items(processSaleItems(saleLineMatcher.group(3)))
                .salesmanName(saleLineMatcher.group(4))
                .build();

        final var totalSaleValue = sale.calculateSaleTotalPrice();

        if (fileReport.getTotalSalesBySalesMan().containsKey(sale.getSalesmanName())) {
            fileReport.getTotalSalesBySalesMan().put(sale.getSalesmanName(),
                    fileReport.getTotalSalesBySalesMan().get(sale.getSalesmanName()).add(totalSaleValue));
        } else {
            fileReport.getTotalSalesBySalesMan().put(sale.getSalesmanName(), totalSaleValue);
        }

        if (Objects.isNull(fileReport.getMostExpensiveSalePrice()) || totalSaleValue.compareTo(fileReport.getMostExpensiveSalePrice()) == 1) {
            fileReport.setMostExpensiveSalePrice(totalSaleValue);
            fileReport.setMostExpensiveSaleId(sale.getId());
        }

        return fileReport;
    }

    private List<SaleItemVO> processSaleItems(String itemsArray) {

        var saleItemsList = new ArrayList<SaleItemVO>();
        Matcher saleItemMatcher = Pattern.compile(SALE_REGEX_PATTERN).matcher(itemsArray);

        while (saleItemMatcher.find()) {
            var saleItemDetailsStr = saleItemMatcher.group();
            Matcher saleItemDetailsMatcher = Pattern.compile(SALE_ITEM_REGEX_PATTERN).matcher(saleItemDetailsStr);

            if (saleItemDetailsMatcher.find()) {
                saleItemsList.add(SaleItemVO.builder()
                        .id(Long.valueOf(saleItemDetailsMatcher.group(1)))
                        .quantity(Integer.valueOf(saleItemDetailsMatcher.group(2)))
                        .price(new BigDecimal(saleItemDetailsMatcher.group(3)))
                        .build());
            } else {
                throw new GenericException("Invalid file line -> " + saleItemDetailsStr);
            }
        }

        return saleItemsList;
    }
}
