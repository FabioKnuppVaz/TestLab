package br.com.testlab.usecases;

import br.com.testlab.dto.EmpregadoDto;
import br.com.testlab.entities.Empregado;
import br.com.testlab.persistence.jpa.EmpregadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EmpregadoAtualizar {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity update(@RequestBody EmpregadoDto empregadoDto) {
        try {
            return empregadoRepository.findByNrEmpregado(empregadoDto.getNrEmpregado())
                    .map(record -> {
                        modelMapper.map(empregadoDto, Empregado.class);

                        record.setNrEmpregado(empregadoDto.getNrEmpregado());
                        record.setDsCargo(empregadoDto.getDsCargo());
                        record.setNrGerente(empregadoDto.getNrGerente());
                        record.setDtAdmissao(empregadoDto.getDtAdmissao());
                        record.setVlComissao(empregadoDto.getVlComissao());
                        record.setVlSalario(empregadoDto.getVlSalario());
                        record.setNmEmpregado(empregadoDto.getNmEmpregado());

                        Empregado empregado = modelMapper.map(record, Empregado.class);
                        empregadoRepository.save(empregado);

                        return ResponseEntity.ok().body(empregadoDto);
                    }).orElse(ResponseEntity.notFound().build());
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
