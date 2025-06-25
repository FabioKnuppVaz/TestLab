package br.com.testlab.usecases.empregado;

import br.com.testlab.dto.EmpregadoDto;
import br.com.testlab.persistence.jpa.EmpregadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpregadoBuscarTodos {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity findAll() {
        try {
            List<EmpregadoDto> response = empregadoRepository.findAll().stream().map(record -> {
                EmpregadoDto empregadoDto = modelMapper.map(record, EmpregadoDto.class);
                return empregadoDto;
            }).collect(Collectors.toList());
            return ResponseEntity.ok().body(response);
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
