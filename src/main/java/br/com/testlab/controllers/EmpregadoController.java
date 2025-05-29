package br.com.testlab.controllers;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.models.Depto;
import br.com.testlab.models.Empregado;
import br.com.testlab.repositories.EmpregadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public EmpregadoDto findById(@RequestParam Integer nrEmpregado) {
        Empregado empregado = empregadoRepository
                              .findById(nrEmpregado)
                              .get();

        EmpregadoDto empregadoDto = modelMapper.map(empregado, EmpregadoDto.class);
        return empregadoDto;
    }

    @DeleteMapping("deleteById")
    public void deleteById(@RequestParam Integer nrEmpregado) {
        empregadoRepository.deleteById(nrEmpregado);
    }

    @PatchMapping("replaceById")
    public void replaceById(@RequestBody EmpregadoDto empregadoDto) {
        Optional<Empregado> empregadoToReplace = empregadoRepository.findById(empregadoDto.getNrEmpregado());

        if(!empregadoToReplace.isEmpty()) {
            empregadoRepository.save(modelMapper.map(empregadoDto, Empregado.class));
        }
    }

    @PutMapping("insert")
    public void insert(@RequestBody EmpregadoDto empregadoDto) {
        empregadoRepository.save(modelMapper.map(empregadoDto, Empregado.class));
    }

}
