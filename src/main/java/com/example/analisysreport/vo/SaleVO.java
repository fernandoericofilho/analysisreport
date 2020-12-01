package com.example.analisysreport.vo;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleVO {

    private Long id;
    private List<SaleItemVO> items;
    private String salesmanName;

    public BigDecimal calculateSaleTotalPrice() {
        return items.stream()
                .map(SaleItemVO::calculateSaleItemPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
