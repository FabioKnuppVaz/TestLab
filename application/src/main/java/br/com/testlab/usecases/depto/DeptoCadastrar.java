package br.com.testlab.usecases.depto;

import br.com.testlab.dto.DeptoDto;
import br.com.testlab.entities.Depto;
import br.com.testlab.persistence.jpa.DeptoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class DeptoCadastrar {

    @Autowired
    DeptoRepository deptoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity create(@RequestBody DeptoDto deptoDto){
        try {
            Depto depto = modelMapper.map(deptoDto, Depto.class);
            deptoRepository.save(depto);
            modelMapper.map(depto, DeptoDto.class);
            return ResponseEntity.ok().body(deptoDto);
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
