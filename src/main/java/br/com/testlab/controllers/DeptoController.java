package br.com.testlab.controllers;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.models.Depto;
import br.com.testlab.repositories.DeptoRepository;
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
@RequestMapping("depto")
@Transactional
@Tag(name = "Departamentos", description = "Gerenciamento de departamentos")
public class DeptoController {

    @Autowired
    private DeptoRepository deptoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Buscar todos departamentos")
    @GetMapping("findAll")
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

    @Operation(summary = "Cadastrar um departamento")
    @PostMapping("create")
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

    @Operation(summary = "Buscar um departamento")
    @GetMapping("read")
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

    @Operation(summary = "Atualizar um departamento")
    @PutMapping("update")
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

    @Operation(summary = "Deletar um departamento")
    @DeleteMapping("delete")
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
