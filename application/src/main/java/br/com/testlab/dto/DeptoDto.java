package br.com.testlab.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptoDto {

    Integer nrDepto;
    String nmDepto;
    String dsLocal;
    BigDecimal vlOrcamento;

}
