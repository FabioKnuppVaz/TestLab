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
public class DeptoAtualizar {

    @Autowired
    DeptoRepository deptoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity update(@RequestBody DeptoDto deptoDto) {
        try {
            return deptoRepository.findByNrDepto(deptoDto.getNrDepto())
                    .map(record -> {
                        record.setNrDepto(deptoDto.getNrDepto());
                        record.setNmDepto(deptoDto.getNmDepto());
                        record.setDsLocal(deptoDto.getDsLocal());
                        record.setVlOrcamento(deptoDto.getVlOrcamento());

                        Depto depto = modelMapper.map(record, Depto.class);
                        deptoRepository.save(depto);

                        return ResponseEntity.ok().body(deptoDto);
                    }).orElse(ResponseEntity.notFound().build());
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
