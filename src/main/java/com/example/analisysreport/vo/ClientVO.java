package com.example.analisysreport.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientVO {

    private Long id;
    private Integer cnpj;
    private String name;
    private String businessArea;

}
