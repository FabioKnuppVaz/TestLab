package br.com.testlab.usecases.depto;

import br.com.testlab.dto.DeptoDto;
import br.com.testlab.persistence.jpa.DeptoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeptoBuscarTodos {

    @Autowired
    DeptoRepository deptoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity findAll(){
        try {
            List<DeptoDto> response = deptoRepository.findAll().stream().map(record -> {
                DeptoDto deptoDto = modelMapper.map(record, DeptoDto.class);
                return deptoDto;
            }).collect(Collectors.toList());
            return ResponseEntity.ok().body(response);
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
