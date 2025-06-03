package br.com.testlab.services;

import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.models.Empregado;
import br.com.testlab.repositories.EmpregadoRepository;
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

class EmpregadoServiceTest {

    @Mock
    private EmpregadoRepository empregadoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmpregadoService empregadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Empregado e1 = new Empregado();
        Empregado e2 = new Empregado();
        EmpregadoDto dto1 = new EmpregadoDto();
        EmpregadoDto dto2 = new EmpregadoDto();

        when(empregadoRepository.findAll()).thenReturn(asList(e1, e2));
        when(modelMapper.map(e1, EmpregadoDto.class)).thenReturn(dto1);
        when(modelMapper.map(e2, EmpregadoDto.class)).thenReturn(dto2);

        List<EmpregadoDto> result = empregadoService.findAll();

        assertEquals(2, result.size());
        verify(empregadoRepository).findAll();
    }

    @Test
    void testFindById() {
        int id = 1;
        Empregado empregado = new Empregado();
        EmpregadoDto dto = new EmpregadoDto();

        when(empregadoRepository.findById(id)).thenReturn(Optional.of(empregado));
        when(modelMapper.map(empregado, EmpregadoDto.class)).thenReturn(dto);

        EmpregadoDto result = empregadoService.findById(id);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void testDeleteById() {
        int id = 1;

        empregadoService.deleteById(id);

        verify(empregadoRepository, times(1)).deleteById(id);
    }

    @Test
    void testReplaceById_WhenExists() {
        EmpregadoDto dto = new EmpregadoDto();
        dto.setNrEmpregado(1);
        Empregado empregado = new Empregado();

        when(empregadoRepository.findById(1)).thenReturn(Optional.of(new Empregado()));
        when(modelMapper.map(dto, Empregado.class)).thenReturn(empregado);
        when(empregadoRepository.save(empregado)).thenReturn(empregado);
        when(modelMapper.map(empregado, EmpregadoDto.class)).thenReturn(dto);

        EmpregadoDto result = empregadoService.replaceById(dto);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void testReplaceById_WhenNotExists() {
        EmpregadoDto dto = new EmpregadoDto();
        dto.setNrEmpregado(1);

        when(empregadoRepository.findById(1)).thenReturn(Optional.empty());

        EmpregadoDto result = empregadoService.replaceById(dto);

        assertNull(result);
    }

    @Test
    void testInsert() {
        EmpregadoDto dto = new EmpregadoDto();
        Empregado empregado = new Empregado();

        when(modelMapper.map(dto, Empregado.class)).thenReturn(empregado);

        empregadoService.insert(dto);

        verify(empregadoRepository, times(1)).save(empregado);
    }
}
