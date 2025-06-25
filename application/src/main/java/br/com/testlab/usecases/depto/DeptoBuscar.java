package br.com.testlab.usecases.depto;

import br.com.testlab.dto.DeptoDto;
import br.com.testlab.persistence.jpa.DeptoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class DeptoBuscar {

    @Autowired
    DeptoRepository deptoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity read(@RequestParam Integer nrDepto) {
        try {
            return deptoRepository.findByNrDepto(nrDepto)
                    .map(record -> {
                        DeptoDto deptoDto = modelMapper.map(record, DeptoDto.class);
                        return ResponseEntity.ok().body(deptoDto);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
