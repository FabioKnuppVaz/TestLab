package br.com.testlab.models;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Depto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer nrDepto;
	String nmDepto;
	String dsLocal;
	BigDecimal vlOrcamento;

	@OneToMany(mappedBy = "depto", cascade = CascadeType.ALL)
	List<Empregado> empregados;

}
