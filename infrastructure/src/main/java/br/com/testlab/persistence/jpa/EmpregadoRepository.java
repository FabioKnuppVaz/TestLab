package br.com.testlab.persistence.jpa;

import br.com.testlab.entities.Empregado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpregadoRepository extends JpaRepository<Empregado, Integer> {

    Optional<Empregado> findByNrEmpregado(Integer nrEmpregado);
    void deleteByNrEmpregado(Integer nrEmpregado);

}
