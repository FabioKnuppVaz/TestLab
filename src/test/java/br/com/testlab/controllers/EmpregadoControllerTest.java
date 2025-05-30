package br.com.testlab.controllers;

import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.models.Empregado;
import br.com.testlab.repositories.EmpregadoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmpregadoControllerTest {

    @Mock
    private EmpregadoRepository empregadoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmpregadoController empregadoController;


    private Empregado empregado1;
    private Empregado empregado2;
    private EmpregadoDto dto1;
    private EmpregadoDto dto2;

    @Before
    public void setUp() {
        empregado1 = new Empregado();
        empregado2 = new Empregado();

        dto1 = new EmpregadoDto();
        dto2 = new EmpregadoDto();

        empregado1.setNrEmpregado(1);
        empregado1.setNmEmpregado("João");

        empregado2.setNrEmpregado(2);
        empregado2.setNmEmpregado("Maria");

        dto1.setNrEmpregado(1);
        dto1.setNmEmpregado("João");

        dto2.setNrEmpregado(2);
        dto2.setNmEmpregado("Maria");
    }

    @Test
    public void findAllTest() {
        when(empregadoRepository.findAll()).thenReturn(Arrays.asList(empregado1, empregado2));
        when(modelMapper.map(empregado1, EmpregadoDto.class)).thenReturn(dto1);
        when(modelMapper.map(empregado2, EmpregadoDto.class)).thenReturn(dto2);

        List<EmpregadoDto> resultado = empregadoController.getAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(dto1, resultado.get(0));
        assertEquals(dto2, resultado.get(1));
    }

    @Test
    public void findByIdTest() {

    }

    @Test
    public void deleteByIdTest() {

    }

    @Test
    public void replaceByIdTest() {

    }

    @Test
    public void insertTest() {

    }

}
