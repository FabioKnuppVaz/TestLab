package br.com.testlab.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpregadoDto {

    Integer nrEmpregado;
    String nmEmpregado;
    Integer nrGerente;
    String dsCargo;
    Date dtAdmissao;
    BigDecimal vlSalario;
    BigDecimal vlComissao;

}
