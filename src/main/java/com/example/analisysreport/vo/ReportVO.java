package com.example.analisysreport.vo;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportVO {

    private Integer clientsAmount;
    private Integer salesManAmount;
    private Long mostExpensiveSaleId;
    private BigDecimal mostExpensiveSalePrice;
    private String worstSellerName;
    private HashMap<String, BigDecimal> totalSalesBySalesMan;

    public ReportVO incrementClientsAmount() {
        clientsAmount++;
        return this;
    }

    public ReportVO incrementSalesMensAmount() {
        salesManAmount++;
        return this;
    }

    public List<String> toStringList() {

        final var data = new ArrayList<String>();
        addNonEmptyInfoInStringList(data, "Quantidade de clientes do arquivo de entrada: ", clientsAmount);
        addNonEmptyInfoInStringList(data, "Quantidade de vendedores no arquivo de entrada: ", salesManAmount);
        addNonEmptyInfoInStringList(data, "Id da venda mais cara: ", mostExpensiveSaleId);
        addNonEmptyInfoInStringList(data, "O Pior vendedor: ", getWorstSalesmanName());

        return data;
    }

    public String getWorstSalesmanName() {
        if (totalSalesBySalesMan.isEmpty()) return Strings.EMPTY;
        return  totalSalesBySalesMan.entrySet().stream()
                .min(Comparator.comparing(Map.Entry::getValue))
                .orElseGet(null)
                .getKey();
    }

    private void addNonEmptyInfoInStringList(List<String> list, final String description, final Object value) {
        if (Objects.nonNull(value) && !StringUtils.isEmpty(value.toString())) {
            list.add(description + value.toString());
        }
    }
}
