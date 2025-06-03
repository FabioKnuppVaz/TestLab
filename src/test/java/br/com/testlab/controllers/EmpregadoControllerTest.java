package br.com.testlab.controllers;

import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.services.EmpregadoService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmpregadoControllerTest {

    @InjectMocks
    private EmpregadoController empregadoController;

    @Mock
    private EmpregadoService empregadoService;

    private EmpregadoDto empregadoDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        empregadoDto = new EmpregadoDto();
        empregadoDto.setNrEmpregado(1);
    }

    @Test
    public void testGetAll_ReturnsOk() {
        when(empregadoService.findAll()).thenReturn(Arrays.asList(empregadoDto));

        ResponseEntity<?> response = empregadoController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAll_ReturnsNotFound() {
        when(empregadoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = empregadoController.getAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetAll_ReturnsBadRequestOnException() {
        when(empregadoService.findAll()).thenThrow(new RuntimeException());

        ResponseEntity<?> response = empregadoController.getAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindById_ReturnsOk() {
        when(empregadoService.findById(1)).thenReturn(empregadoDto);

        ResponseEntity<?> response = empregadoController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empregadoDto, response.getBody());
    }

    @Test
    public void testFindById_ReturnsNotFound() {
        when(empregadoService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = empregadoController.findById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFindById_ReturnsBadRequestOnException() {
        when(empregadoService.findById(1)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = empregadoController.findById(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteById_ReturnsAccepted() {
        doNothing().when(empregadoService).deleteById(1);

        ResponseEntity<?> response = empregadoController.deleteById(1);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testDeleteById_ReturnsBadRequestOnException() {
        doThrow(new RuntimeException()).when(empregadoService).deleteById(1);

        ResponseEntity<?> response = empregadoController.deleteById(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // @TODO Corrigir
    @Ignore
    @Test
    public void testReplaceById_ReturnsOk() {
        when(empregadoService.findById(1)).thenReturn(empregadoDto);
        //when(empregadoService.replaceById(empregadoDto)).thenReturn(empregadoDto);

        ResponseEntity<?> response = empregadoController.replaceById(empregadoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(empregadoDto, response.getBody());
    }

    @Test
    public void testReplaceById_ReturnsNotFound() {
        when(empregadoService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = empregadoController.replaceById(empregadoDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testReplaceById_ReturnsBadRequestOnException() {
        when(empregadoService.findById(1)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = empregadoController.replaceById(empregadoDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testInsert_ReturnsCreated() {
        doNothing().when(empregadoService).insert(empregadoDto);

        ResponseEntity<?> response = empregadoController.insert(empregadoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(empregadoDto, response.getBody());
    }

    @Test
    public void testInsert_ReturnsBadRequestOnException() {
        doThrow(new RuntimeException()).when(empregadoService).insert(empregadoDto);

        ResponseEntity<?> response = empregadoController.insert(empregadoDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
