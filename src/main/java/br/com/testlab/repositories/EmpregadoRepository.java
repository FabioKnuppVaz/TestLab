package br.com.testlab.repositories;

import br.com.testlab.models.Empregado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpregadoRepository extends JpaRepository<Empregado, Integer> {

    Optional<Empregado> findByNrEmpregado(Integer nrEmpregado);
    void deleteByNrEmpregado(Integer nrEmpregado);

}
