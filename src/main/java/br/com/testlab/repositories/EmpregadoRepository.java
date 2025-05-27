package br.com.testlab.repositories;

import br.com.testlab.models.Empregado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpregadoRepository extends JpaRepository<Empregado, Integer> {

}
