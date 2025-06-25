package br.com.testlab.usecases.empregado;

import br.com.testlab.dto.EmpregadoDto;
import br.com.testlab.persistence.jpa.EmpregadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class EmpregadoBuscar {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity read(@RequestParam Integer nrEmpregado) {
        try {
            return empregadoRepository.findByNrEmpregado(nrEmpregado)
                    .map(record -> {
                        EmpregadoDto empregadoDto = modelMapper.map(record, EmpregadoDto.class);
                        return ResponseEntity.ok().body(empregadoDto);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
