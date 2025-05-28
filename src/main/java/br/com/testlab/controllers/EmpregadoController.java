package br.com.testlab.controllers;

import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.models.Empregado;
import br.com.testlab.repositories.EmpregadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("empregado")
public class EmpregadoController {

    @Autowired
    EmpregadoRepository empregadoRepository;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("all")
    public List<EmpregadoDto> getAll() {
        List<EmpregadoDto> empregadosDto = empregadoRepository
                                           .findAll()
                                           .stream()
                                           .map(empregadoModel -> modelMapper.map(empregadoModel, EmpregadoDto.class))
                                           .toList();
        return empregadosDto;
    }

    @GetMapping("findById")
    public EmpregadoDto findById(@RequestParam(value = "nrEmpregado") Integer nrEmpregado) {
        Empregado empregado = empregadoRepository
                              .findById(nrEmpregado)
                              .get();

        EmpregadoDto empregadoDto = modelMapper.map(empregado, EmpregadoDto.class);
        return empregadoDto;
    }

    @DeleteMapping("deleteById")
    public void deleteById(@RequestParam(value = "nrEmpregado") Integer nrEmpregado) {
        empregadoRepository.deleteById(nrEmpregado);
    }

}
