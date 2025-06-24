package br.com.testlab.usecases;

import br.com.testlab.persistence.jpa.EmpregadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmpregadoDeletar {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity delete(Integer nrEmpregado) {
        try {
            return empregadoRepository.findByNrEmpregado(nrEmpregado)
                    .map(record -> {
                        empregadoRepository.deleteByNrEmpregado(nrEmpregado);
                        return ResponseEntity.ok().build();
                    }).orElse(ResponseEntity.notFound().build());
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
