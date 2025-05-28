package br.com.testlab.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeptoDto {

    Integer nrDepto;
    String nmDepto;
    String dsLocal;
    BigDecimal vlOrcamento;

}
