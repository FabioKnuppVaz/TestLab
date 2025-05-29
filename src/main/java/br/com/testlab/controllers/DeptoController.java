package br.com.testlab.controllers;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.models.Depto;
import br.com.testlab.repositories.DeptoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("depto")
public class DeptoController {

    @Autowired
    DeptoRepository deptoRepository;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("findAll")
    public List<DeptoDto> findAll() {
        List<DeptoDto> deptosDto = deptoRepository
                                   .findAll()
                                   .stream()
                                   .map(deptoModel -> modelMapper.map(deptoModel, DeptoDto.class))
                                   .toList();
        return deptosDto;
    }

    @GetMapping("findById")
    public DeptoDto findById(@RequestParam(value = "nrDepto") Integer nrDepto) {
        Depto depto = deptoRepository
                      .findById(nrDepto)
                      .get();

        DeptoDto deptoDto = modelMapper.map(depto, DeptoDto.class);
        return deptoDto;
    }

    @DeleteMapping("deleteById")
    public void deleteById(@RequestParam(value = "nrDepto") Integer nrDepto) {
        deptoRepository.deleteById(nrDepto);
    }

    @PatchMapping("replaceById")
    public void replaceById(DeptoDto deptoDto) {
        Optional<Depto> deptoToReplace = deptoRepository.findById(deptoDto.getNrDepto());

        if(!deptoToReplace.isEmpty()) {
            deptoRepository.save(modelMapper.map(deptoDto, Depto.class));
        }
    }

}
