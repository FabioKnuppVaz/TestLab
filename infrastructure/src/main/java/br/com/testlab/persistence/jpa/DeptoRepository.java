package br.com.testlab.persistence.jpa;


import br.com.testlab.entities.Depto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeptoRepository extends JpaRepository<Depto, Integer> {

    Optional<Depto> findByNrDepto(Integer nrDepto);
    void deleteByNrDepto(Integer nrDepto);

}
