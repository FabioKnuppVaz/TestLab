package br.com.testlab.services;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.models.Depto;
import br.com.testlab.repositories.DeptoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeptoServiceTest {

    @Mock
    private DeptoRepository deptoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DeptoService deptoService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Depto depto1 = new Depto();
        Depto depto2 = new Depto();
        DeptoDto dto1 = new DeptoDto();
        DeptoDto dto2 = new DeptoDto();

        when(deptoRepository.findAll()).thenReturn(asList(depto1, depto2));
        when(modelMapper.map(depto1, DeptoDto.class)).thenReturn(dto1);
        when(modelMapper.map(depto2, DeptoDto.class)).thenReturn(dto2);

        List<DeptoDto> result = deptoService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        int id = 1;
        Depto depto = new Depto();
        DeptoDto dto = new DeptoDto();

        when(deptoRepository.findById(id)).thenReturn(Optional.of(depto));
        when(modelMapper.map(depto, DeptoDto.class)).thenReturn(dto);

        DeptoDto result = deptoService.findById(id);

        assertEquals(dto, result);
    }

    @Test
    public void testDeleteById() {
        int id = 1;

        deptoService.deleteById(id);

        verify(deptoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testReplaceByIdWhenExists() {
        DeptoDto dto = new DeptoDto();
        dto.setNrDepto(1);
        Depto depto = new Depto();

        when(deptoRepository.findById(1)).thenReturn(Optional.of(depto));
        when(modelMapper.map(dto, Depto.class)).thenReturn(depto);
        when(deptoRepository.save(depto)).thenReturn(depto);
        when(modelMapper.map(depto, DeptoDto.class)).thenReturn(dto);

        DeptoDto result = deptoService.replaceById(dto);

        assertEquals(dto, result);
    }

    @Test
    public void testReplaceByIdWhenNotExists() {
        DeptoDto dto = new DeptoDto();
        dto.setNrDepto(1);

        when(deptoRepository.findById(1)).thenReturn(Optional.empty());

        DeptoDto result = deptoService.replaceById(dto);

        assertNull(result);
    }

    @Test
    public void testInsert() {
        DeptoDto dto = new DeptoDto();
        Depto depto = new Depto();

        when(modelMapper.map(dto, Depto.class)).thenReturn(depto);
        when(deptoRepository.save(depto)).thenReturn(depto);
        when(modelMapper.map(depto, DeptoDto.class)).thenReturn(dto);

        DeptoDto result = deptoService.insert(dto);

        assertEquals(dto, result);
    }

}
