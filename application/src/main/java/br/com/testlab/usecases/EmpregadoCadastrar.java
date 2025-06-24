package br.com.testlab.usecases;

import br.com.testlab.dto.EmpregadoDto;
import br.com.testlab.entities.Empregado;
import br.com.testlab.persistence.jpa.EmpregadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmpregadoCadastrar {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity create(EmpregadoDto empregadoDto) {
        try {
            Empregado empregado = modelMapper.map(empregadoDto, Empregado.class);
            empregadoRepository.save(empregado);
            modelMapper.map(empregado, EmpregadoDto.class);
            return ResponseEntity.ok().body(empregadoDto);
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
