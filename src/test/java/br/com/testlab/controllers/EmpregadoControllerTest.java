package br.com.testlab.controllers;

import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.models.Empregado;
import br.com.testlab.repositories.EmpregadoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@RunWith(MockitoJUnitRunner.class)
public class EmpregadoControllerTest {

    @InjectMocks
    private EmpregadoController empregadoController;

    @Mock
    private EmpregadoRepository empregadoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void findAllSuccessTest() {
        Empregado emp1 = Empregado.builder().nrEmpregado(1).build();
        Empregado emp2 = Empregado.builder().nrEmpregado(2).build();

        EmpregadoDto dto1 = EmpregadoDto.builder().nrEmpregado(1).build();
        EmpregadoDto dto2 = EmpregadoDto.builder().nrEmpregado(2).build();

        when(empregadoRepository.findAll()).thenReturn(Arrays.asList(emp1, emp2));
        when(modelMapper.map(emp1, EmpregadoDto.class)).thenReturn(dto1);
        when(modelMapper.map(emp2, EmpregadoDto.class)).thenReturn(dto2);

        ResponseEntity response = empregadoController.findAll();

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, ((List<EmpregadoDto>) response.getBody()).size());
    }

    @Test
    public void findAllExceptionTest() {
        when(empregadoRepository.findAll()).thenThrow(new RuntimeException());
        ResponseEntity response = empregadoController.findAll();
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void createSuccessTest() {
        EmpregadoDto dto = EmpregadoDto.builder().nrEmpregado(1).nmEmpregado("Ana").build();
        Empregado emp = Empregado.builder().nrEmpregado(1).nmEmpregado("Ana").build();

        when(modelMapper.map(dto, Empregado.class)).thenReturn(emp);

        ResponseEntity response = empregadoController.create(dto);

        assertEquals(OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(empregadoRepository).save(emp);
    }

    @Test
    public void updateSuccessTest() {
        EmpregadoDto dto = EmpregadoDto.builder()
                                       .nrEmpregado(10)
                                       .nmEmpregado("Maria")
                                       .dsCargo("Dev")
                                       .vlSalario(new BigDecimal("3000"))
                                       .dtAdmissao(new Date())
                                       .build();

        Empregado emp = Empregado.builder().nrEmpregado(10).nmEmpregado("Maria").build();

        when(empregadoRepository.findByNrEmpregado(10)).thenReturn(Optional.of(emp));
        when(modelMapper.map(emp, Empregado.class)).thenReturn(emp);

        ResponseEntity response = empregadoController.update(dto);

        assertEquals(OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
        verify(empregadoRepository).save(any());
    }

    @Test
    public void readSuccessTest() {
        Integer nr = 5;
        Empregado emp = Empregado.builder().nrEmpregado(nr).nmEmpregado("Carlos").build();
        EmpregadoDto dto = EmpregadoDto.builder().nrEmpregado(nr).nmEmpregado("Carlos").build();

        when(empregadoRepository.findByNrEmpregado(nr)).thenReturn(Optional.of(emp));
        when(modelMapper.map(emp, EmpregadoDto.class)).thenReturn(dto);

        ResponseEntity response = empregadoController.read(nr);

        assertEquals(OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    public void readNotFoundTest() {
        when(empregadoRepository.findByNrEmpregado(1)).thenReturn(Optional.empty());
        ResponseEntity response = empregadoController.read(1);
        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteSuccessTest() {
        Integer nr = 9;
        Empregado emp = Empregado.builder().nrEmpregado(nr).build();

        when(empregadoRepository.findByNrEmpregado(nr)).thenReturn(Optional.of(emp));

        ResponseEntity response = empregadoController.delete(nr);

        assertEquals(OK, response.getStatusCode());
        verify(empregadoRepository).deleteByNrEmpregado(nr);
    }

    @Test
    public void deleteNotFoundTest() {
        when(empregadoRepository.findByNrEmpregado(1)).thenReturn(Optional.empty());
        ResponseEntity response = empregadoController.delete(1);
        assertEquals(NOT_FOUND, response.getStatusCode());
    }

}
