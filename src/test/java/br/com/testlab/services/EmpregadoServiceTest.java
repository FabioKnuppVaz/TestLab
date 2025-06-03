package br.com.testlab.services;

import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.models.Empregado;
import br.com.testlab.repositories.EmpregadoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmpregadoServiceTest {

    @InjectMocks
    private EmpregadoService empregadoService;

    @Mock
    private EmpregadoRepository empregadoRepository;

    @Mock
    private ModelMapper modelMapper;

    private Empregado empregado;
    private EmpregadoDto empregadoDto;

    @Before
    public void setUp() {
        empregado = new Empregado();
        empregado.setNrEmpregado(1);
        empregado.setNmEmpregado("João");

        empregadoDto = new EmpregadoDto();
        empregadoDto.setNrEmpregado(1);
        empregadoDto.setNmEmpregado("João");
    }

    @Test
    public void testFindAll() {
        List<Empregado> empregados = Arrays.asList(empregado);
        when(empregadoRepository.findAll()).thenReturn(empregados);
        when(modelMapper.map(empregado, EmpregadoDto.class)).thenReturn(empregadoDto);

        List<EmpregadoDto> result = empregadoService.findAll();

        assertEquals(1, result.size());
        assertEquals(empregadoDto.getNrEmpregado(), result.get(0).getNrEmpregado());
        verify(empregadoRepository).findAll();
    }

    @Test
    public void testFindById() {
        when(empregadoRepository.findById(1)).thenReturn(Optional.of(empregado));
        when(modelMapper.map(empregado, EmpregadoDto.class)).thenReturn(empregadoDto);

        EmpregadoDto result = empregadoService.findById(1);

        assertNotNull(result);
        assertEquals(empregadoDto.getNrEmpregado(), result.getNrEmpregado());
        verify(empregadoRepository).findById(1);
    }

    @Test
    public void testDeleteById() {
        empregadoService.deleteById(1);
        verify(empregadoRepository).deleteById(1);
    }

    @Test
    public void testReplaceByIdExists() {
        when(empregadoRepository.findById(1)).thenReturn(Optional.of(empregado));
        when(modelMapper.map(empregadoDto, Empregado.class)).thenReturn(empregado);

        empregadoService.replaceById(empregadoDto);

        verify(empregadoRepository).save(empregado);
    }

    @Test
    public void testReplaceByIdNotExists() {
        when(empregadoRepository.findById(1)).thenReturn(Optional.empty());

        empregadoService.replaceById(empregadoDto);

        verify(empregadoRepository, never()).save(any());
    }

    @Test
    public void testInsert() {
        when(modelMapper.map(empregadoDto, Empregado.class)).thenReturn(empregado);

        empregadoService.insert(empregadoDto);

        verify(empregadoRepository).save(empregado);
    }
}