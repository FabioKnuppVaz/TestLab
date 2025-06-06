package br.com.testlab.controllers;

import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.services.EmpregadoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("empregado")
public class EmpregadoController {

    @Autowired
    private EmpregadoService empregadoService;

    @GetMapping("all")
    public ResponseEntity getAll() {
        List<EmpregadoDto> empregadosDto;
        try {
            empregadosDto = empregadoService.findAll();
            if (empregadosDto.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            log.error("Erro ao retornar todos empregados: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(empregadosDto, HttpStatus.OK);
    }

    @GetMapping("findById")
    public ResponseEntity findById(@RequestParam Integer nrEmpregado) {
        EmpregadoDto empregadoDto;
        try {
            empregadoDto = empregadoService.findById(nrEmpregado);
            if (empregadoDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            log.error("Erro ao procurar empregado: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(empregadoDto, HttpStatus.OK);
    }

    @DeleteMapping("deleteById")
    public ResponseEntity deleteById(@RequestParam Integer nrEmpregado) {
        try {
            empregadoService.deleteById(nrEmpregado);
        } catch(Exception e) {
            log.error("Erro ao deletar empregado: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("replaceById")
    public ResponseEntity replaceById(@RequestBody EmpregadoDto empregadoDto) {
        try {
            empregadoDto = empregadoService.findById(empregadoDto.getNrEmpregado());
            if (empregadoDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                empregadoService.replaceById(empregadoDto);
            }
        } catch(Exception e) {
            log.error("Erro ao modificar empregado: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(empregadoDto, HttpStatus.OK);
    }

    @PostMapping("insert")
    public ResponseEntity insert(@RequestBody EmpregadoDto empregadoDto) {
        try {
            empregadoService.insert(empregadoDto);
            if (empregadoDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch(Exception e) {
            log.error("Erro ao inserir empregado: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(empregadoDto, HttpStatus.CREATED);
    }

}
