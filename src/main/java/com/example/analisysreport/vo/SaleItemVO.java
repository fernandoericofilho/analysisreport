package com.example.analisysreport.vo;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaleItemVO {

    private Long id;
    private Integer quantity;
    private BigDecimal price;

    public BigDecimal calculateSaleItemPrice() {
        return price.multiply(new BigDecimal(quantity));
    }
}
