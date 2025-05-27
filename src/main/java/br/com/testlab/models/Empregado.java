package br.com.testlab.models;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
public class Empregado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
