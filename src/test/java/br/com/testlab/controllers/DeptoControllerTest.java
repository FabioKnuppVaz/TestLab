package br.com.testlab.controllers;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.services.DeptoService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeptoControllerTest {

    @InjectMocks
    private DeptoController deptoController;

    @Mock
    private DeptoService deptoService;

    private DeptoDto deptoDto;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        deptoDto = new DeptoDto();
        deptoDto.setNrDepto(1);
    }

    @Test
    public void testFindAll_ReturnsOk() {
        when(deptoService.findAll()).thenReturn(asList(deptoDto));

        ResponseEntity<?> response = deptoController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testFindAll_ReturnsNotFound() {
        when(deptoService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = deptoController.findAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFindAll_ReturnsBadRequestOnException() {
        when(deptoService.findAll()).thenThrow(new RuntimeException());

        ResponseEntity<?> response = deptoController.findAll();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testFindById_ReturnsOk() {
        when(deptoService.findById(1)).thenReturn(deptoDto);

        ResponseEntity<?> response = deptoController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deptoDto, response.getBody());
    }

    @Test
    public void testFindById_ReturnsNotFound() {
        when(deptoService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = deptoController.findById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFindById_ReturnsBadRequestOnException() {
        when(deptoService.findById(1)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = deptoController.findById(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteById_ReturnsAccepted() {
        doNothing().when(deptoService).deleteById(1);

        ResponseEntity<?> response = deptoController.deleteById(1);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testDeleteById_ReturnsBadRequestOnException() {
        doThrow(new RuntimeException()).when(deptoService).deleteById(1);

        ResponseEntity<?> response = deptoController.deleteById(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testReplaceById_ReturnsOk() {
        when(deptoService.findById(1)).thenReturn(deptoDto);
        when(deptoService.replaceById(deptoDto)).thenReturn(deptoDto);

        ResponseEntity<?> response = deptoController.replaceById(deptoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deptoDto, response.getBody());
    }

    @Test
    public void testReplaceById_ReturnsNotFound() {
        when(deptoService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = deptoController.replaceById(deptoDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testReplaceById_ReturnsBadRequestOnException() {
        when(deptoService.findById(1)).thenThrow(new RuntimeException());

        ResponseEntity<?> response = deptoController.replaceById(deptoDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // @TODO Corrigir
    @Ignore
    @Test
    public void testInsert_ReturnsCreated() {
        //doNothing().when(deptoService).insert(deptoDto);

        ResponseEntity<?> response = deptoController.insert(deptoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testInsert_ReturnsBadRequestOnException() {
        doThrow(new RuntimeException()).when(deptoService).insert(deptoDto);

        ResponseEntity<?> response = deptoController.insert(deptoDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
