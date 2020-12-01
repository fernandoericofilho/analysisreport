package com.example.analisysreport.vo;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SalesmanVO {

    private Long id;
    private String name;
    private Integer cpf;
    private BigDecimal salary;

}
