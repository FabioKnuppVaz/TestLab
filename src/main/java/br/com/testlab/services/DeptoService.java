package br.com.testlab.services;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.models.Depto;
import br.com.testlab.repositories.DeptoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeptoService {

    @Autowired
    private DeptoRepository deptoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DeptoDto> findAll() {
        List<DeptoDto> deptosDto = deptoRepository
                                   .findAll()
                                   .stream()
                                   .map(deptoModel -> modelMapper.map(deptoModel, DeptoDto.class))
                                   .toList();
        return deptosDto;
    }

    public DeptoDto findById(Integer nrDepto) {
        Depto depto = deptoRepository
                      .findById(nrDepto)
                      .get();

        DeptoDto deptoDto = modelMapper.map(depto, DeptoDto.class);
        return deptoDto;
    }

    public void deleteById(Integer nrDepto) {
        deptoRepository.deleteById(nrDepto);
    }

    public DeptoDto replaceById(DeptoDto deptoDto) {
        Optional<Depto> deptoToReplace = deptoRepository.findById(deptoDto.getNrDepto());
        Depto depto = null;

        if(!deptoToReplace.isEmpty()) {
            depto = deptoRepository.save(modelMapper.map(deptoDto, Depto.class));
        }
        return modelMapper.map(depto, DeptoDto.class);
    }

    public DeptoDto insert(DeptoDto deptoDto) {
        deptoDto.setNrDepto(null);
        Depto depto = modelMapper.map(deptoDto, Depto.class);
        deptoRepository.save(depto);
        return modelMapper.map(depto, DeptoDto.class);
    }

}
