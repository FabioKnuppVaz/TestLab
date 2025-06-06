package br.com.testlab.controllers;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.services.DeptoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("depto")
public class DeptoController {

    @Autowired
    private DeptoService deptoService;

    @GetMapping("findAll")
    public ResponseEntity findAll() {
        List<DeptoDto> deptosDto;
        try {
            deptosDto = deptoService.findAll();
            if (deptosDto.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            log.error("Erro ao procurar todos apartamentos: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(deptosDto, HttpStatus.OK);
    }

    @GetMapping("findById")
    public ResponseEntity findById(@RequestParam Integer nrDepto) {
        DeptoDto deptosDto;
        try {
            deptosDto = deptoService.findById(nrDepto);
            if (deptosDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            log.error("Erro ao procurar departamento: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(deptosDto, HttpStatus.OK);
    }

    @DeleteMapping("deleteById")
    public ResponseEntity deleteById(@RequestParam Integer nrDepto) {
        try {
            deptoService.deleteById(nrDepto);
        } catch(Exception e) {
            log.error("Erro ao deletar apartamento: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("replaceById")
    public ResponseEntity replaceById(@RequestBody DeptoDto deptoDto) {
        try {
            deptoDto = deptoService.findById(deptoDto.getNrDepto());
            if (deptoDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                deptoDto = deptoService.replaceById(deptoDto);
            }
        } catch(Exception e) {
            log.error("Erro ao modificar departamento: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(deptoDto, HttpStatus.OK);
    }

    @PostMapping("insert")
    public ResponseEntity insert(@RequestBody DeptoDto deptoDto) {
        try {
            deptoService.insert(deptoDto);
            if (deptoDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            log.error("Erro ao inserir departamento: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(deptoDto, HttpStatus.CREATED);
    }

}
