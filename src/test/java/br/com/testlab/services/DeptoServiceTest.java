package br.com.testlab.services;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.models.Depto;
import br.com.testlab.repositories.DeptoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeptoServiceTest {

    @Mock
    private DeptoRepository deptoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DeptoService deptoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Depto depto1 = new Depto();
        Depto depto2 = new Depto();
        DeptoDto dto1 = new DeptoDto();
        DeptoDto dto2 = new DeptoDto();

        when(deptoRepository.findAll()).thenReturn(asList(depto1, depto2));
        when(modelMapper.map(depto1, DeptoDto.class)).thenReturn(dto1);
        when(modelMapper.map(depto2, DeptoDto.class)).thenReturn(dto2);

        List<DeptoDto> result = deptoService.findAll();

        assertEquals(2, result.size());
        verify(deptoRepository).findAll();
    }

    @Test
    void testFindById() {
        int id = 1;
        Depto depto = new Depto();
        DeptoDto dto = new DeptoDto();

        when(deptoRepository.findById(id)).thenReturn(Optional.of(depto));
        when(modelMapper.map(depto, DeptoDto.class)).thenReturn(dto);

        DeptoDto result = deptoService.findById(id);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void testDeleteById() {
        int id = 1;

        deptoService.deleteById(id);

        verify(deptoRepository, times(1)).deleteById(id);
    }

    @Test
    void testReplaceById_WhenExists() {
        int id = 1;
        DeptoDto dto = new DeptoDto();
        dto.setNrDepto(id);
        Depto depto = new Depto();

        when(deptoRepository.findById(id)).thenReturn(Optional.of(depto));
        when(modelMapper.map(dto, Depto.class)).thenReturn(depto);
        when(deptoRepository.save(depto)).thenReturn(depto);
        when(modelMapper.map(depto, DeptoDto.class)).thenReturn(dto);

        DeptoDto result = deptoService.replaceById(dto);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void testReplaceById_WhenNotExists() {
        int id = 1;
        DeptoDto dto = new DeptoDto();
        dto.setNrDepto(id);

        when(deptoRepository.findById(id)).thenReturn(Optional.empty());

        DeptoDto result = deptoService.replaceById(dto);

        assertNull(result);
    }

    @Test
    void testInsert() {
        DeptoDto dto = new DeptoDto();
        Depto depto = new Depto();

        when(modelMapper.map(dto, Depto.class)).thenReturn(depto);
        when(deptoRepository.save(depto)).thenReturn(depto);
        when(modelMapper.map(depto, DeptoDto.class)).thenReturn(dto);

        DeptoDto result = deptoService.insert(dto);

        assertNotNull(result);
        assertEquals(dto, result);
    }
}
