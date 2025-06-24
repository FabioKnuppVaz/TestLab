package br.com.testlab.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empregado {

	@Id
	Integer nrEmpregado;
	String nmEmpregado;
	Integer nrGerente;
	String dsCargo;
	Date dtAdmissao;
	BigDecimal vlSalario;
	BigDecimal vlComissao;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="nr_depto")
	Depto depto;

}
