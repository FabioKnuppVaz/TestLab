package br.com.testlab.services;

import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.models.Empregado;
import br.com.testlab.repositories.EmpregadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpregadoService {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<EmpregadoDto> findAll() {
        List<EmpregadoDto> empregadosDto = empregadoRepository
                                           .findAll()
                                           .stream()
                                           .map(empregadoModel -> modelMapper.map(empregadoModel, EmpregadoDto.class))
                                           .toList();
        return empregadosDto;
    }

    public EmpregadoDto findById(Integer nrEmpregado) {
        Empregado empregado = empregadoRepository
                              .findById(nrEmpregado)
                              .get();

        EmpregadoDto empregadoDto = modelMapper.map(empregado, EmpregadoDto.class);
        return empregadoDto;
    }

    public void deleteById(Integer nrEmpregado) {
        empregadoRepository.deleteById(nrEmpregado);
    }

    public void replaceById(EmpregadoDto empregadoDto) {
        Optional<Empregado> empregadoToReplace = empregadoRepository.findById(empregadoDto.getNrEmpregado());

        if(!empregadoToReplace.isEmpty()) {
            empregadoRepository.save(modelMapper.map(empregadoDto, Empregado.class));
        }
    }

    public void insert(EmpregadoDto empregadoDto) {
        empregadoRepository.save(modelMapper.map(empregadoDto, Empregado.class));
    }

}
