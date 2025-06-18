package br.com.testlab.controllers;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.models.Empregado;
import br.com.testlab.repositories.EmpregadoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("empregado")
@Transactional
@Tag(name = "Empregados", description = "Gerenciamento de empregados")
public class EmpregadoController {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Buscar todos empregados")
    @GetMapping("findAll")
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

    @Operation(summary = "Cadastrar um empregado")
    @PostMapping("create")
    public ResponseEntity create(@RequestBody EmpregadoDto empregadoDto) {
        try {
            Empregado empregado = modelMapper.map(empregadoDto, Empregado.class);
            empregadoRepository.save(empregado);
            modelMapper.map(empregado, EmpregadoDto.class);
            return ResponseEntity.ok().body(empregadoDto);
        } catch(Exception exception) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Buscar um empregado")
    @GetMapping("read")
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

    @Operation(summary = "Atualizar um empregado")
    @PutMapping("update")
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

    @Operation(summary = "Deletar um empregado")
    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestParam Integer nrEmpregado) {
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
