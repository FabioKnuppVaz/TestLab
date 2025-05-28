package br.com.testlab.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class EmpregadoDto {

    Integer nrEmpregado;
    String nmEmpregado;
    Integer nrGerente;
    String dsCargo;
    Date dtAdmissao;
    BigDecimal vlSalario;
    BigDecimal vlComissao;

}
