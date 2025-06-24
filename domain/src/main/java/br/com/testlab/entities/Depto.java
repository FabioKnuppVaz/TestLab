package br.com.testlab.entities;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Depto {

	@Id
	Integer nrDepto;
	String nmDepto;
	String dsLocal;
	BigDecimal vlOrcamento;

	@OneToMany(mappedBy = "depto", cascade = CascadeType.ALL)
	List<Empregado> empregados;

}
