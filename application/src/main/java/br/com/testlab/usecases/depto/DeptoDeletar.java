package br.com.testlab.usecases.depto;

import br.com.testlab.persistence.jpa.DeptoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class DeptoDeletar {

    @Autowired
    DeptoRepository deptoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity delete(@RequestParam Integer nrDepto) {
        try {
            return deptoRepository.findByNrDepto(nrDepto)
                    .map(record -> {
                        deptoRepository.deleteByNrDepto(nrDepto);
                        return ResponseEntity.ok().build();
                    }).orElse(ResponseEntity.notFound().build());
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
