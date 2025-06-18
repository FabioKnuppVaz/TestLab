package br.com.testlab.controllers;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.models.Depto;
import br.com.testlab.repositories.DeptoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@RunWith(MockitoJUnitRunner.class)
public class DeptoControllerTest {

    @InjectMocks
    private DeptoController deptoController;

    @Mock
    private DeptoRepository deptoRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void findAllSuccessTest() {
        Depto depto1 = Depto.builder().nrDepto(1).build();
        Depto depto2 = Depto.builder().nrDepto(2).build();

        List<Depto> mockDeptos = Arrays.asList(depto1, depto2);

        DeptoDto dto1 = DeptoDto.builder().nrDepto(1).build();
        DeptoDto dto2 = DeptoDto.builder().nrDepto(2).build();

        when(deptoRepository.findAll()).thenReturn(mockDeptos);
        when(modelMapper.map(depto1, DeptoDto.class)).thenReturn(dto1);
        when(modelMapper.map(depto2, DeptoDto.class)).thenReturn(dto2);

        ResponseEntity<List<DeptoDto>> response = deptoController.findAll();

        assertEquals(OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(dto1.getNrDepto(), response.getBody().get(0).getNrDepto());
    }

    @Test
    public void findAllExceptionTest() {
        when(deptoRepository.findAll()).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<List<DeptoDto>> response = deptoController.findAll();

        assertEquals(ResponseEntity.internalServerError().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    public void createSuccessTest() {
        DeptoDto inputDto = DeptoDto.builder()
                                    .nrDepto(null)
                                    .nmDepto("Financeiro")
                                    .nrDepto(10)
                                    .vlOrcamento(new BigDecimal("10000"))
                                    .build();

        Depto deptoEntity = Depto.builder()
                                 .nrDepto(null)
                                 .nmDepto("Financeiro")
                                 .nrDepto(10)
                                 .vlOrcamento(new BigDecimal("10000"))
                                 .build();

        when(modelMapper.map(inputDto, Depto.class)).thenReturn(deptoEntity);
        when(modelMapper.map(deptoEntity, DeptoDto.class)).thenReturn(inputDto);

        ResponseEntity response = deptoController.create(inputDto);

        assertEquals(OK, response.getStatusCode());
        assertEquals(inputDto, response.getBody());

        verify(deptoRepository).save(deptoEntity);
    }

    @Test
    public void createExceptionTest() {
        DeptoDto inputDto = DeptoDto.builder()
                                    .nrDepto(null)
                                    .nmDepto("Erro")
                                    .nrDepto(99)
                                    .vlOrcamento(new BigDecimal("0"))
                                    .build();

        Mockito.when(modelMapper.map(inputDto, Depto.class)).thenThrow(new RuntimeException("Falha no mapeamento"));

        ResponseEntity response = deptoController.create(inputDto);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void readSuccessTest() {
        Integer nrDepto = 10;

        Depto depto = Depto.builder()
                           .nrDepto(1)
                           .nmDepto("RH")
                           .nrDepto(nrDepto)
                           .vlOrcamento(new BigDecimal("1000"))
                           .build();

        DeptoDto dto = DeptoDto.builder()
                               .nrDepto(1)
                               .nmDepto("RH")
                               .nrDepto(nrDepto)
                               .vlOrcamento(new BigDecimal("1000"))
                               .build();

        when(deptoRepository.findByNrDepto(nrDepto)).thenReturn(Optional.of(depto));
        when(modelMapper.map(depto, DeptoDto.class)).thenReturn(dto);

        ResponseEntity response = deptoController.read(nrDepto);

        assertEquals(OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }


    @Test
    public void readNotFoundTest() {
        Integer nrDepto = 99;

        when(deptoRepository.findByNrDepto(nrDepto)).thenReturn(Optional.empty());

        ResponseEntity response = deptoController.read(nrDepto);

        Assert.assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void readExceptionTest() {
        Integer nrDepto = 77;

        when(deptoRepository.findByNrDepto(nrDepto)).thenThrow(new RuntimeException("Erro inesperado"));

        ResponseEntity response = deptoController.read(nrDepto);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void updateSuccessTest() {
        DeptoDto inputDto = DeptoDto.builder()
                                    .nrDepto(10)
                                    .nmDepto("Financeiro")
                                    .dsLocal("Bloco A")
                                    .vlOrcamento(new BigDecimal("5000"))
                                    .build();

        Depto existingDepto = Depto.builder()
                                   .nrDepto(1)
                                   .nrDepto(10)
                                   .nmDepto("RH")
                                   .dsLocal("Bloco B")
                                   .vlOrcamento(new BigDecimal("3000"))
                                   .build();

        Depto updatedDepto = Depto.builder()
                                  .nrDepto(1)
                                  .nrDepto(10)
                                  .nmDepto("Financeiro")
                                  .dsLocal("Bloco A")
                                  .vlOrcamento(new BigDecimal("5000"))
                                  .build();

        when(deptoRepository.findByNrDepto(10)).thenReturn(Optional.of(existingDepto));
        when(modelMapper.map(existingDepto, Depto.class)).thenReturn(updatedDepto);

        ResponseEntity response = deptoController.update(inputDto);

        assertEquals(OK, response.getStatusCode());
        assertEquals(inputDto, response.getBody());

        verify(deptoRepository).save(updatedDepto);
    }

    @Test
    public void updateNotFoundTest() {
        DeptoDto inputDto = DeptoDto.builder()
                                    .nrDepto(99)
                                    .nmDepto("Inexistente")
                                    .dsLocal("Bloco Z")
                                    .vlOrcamento(new BigDecimal("100"))
                                    .build();

        when(deptoRepository.findByNrDepto(99)).thenReturn(Optional.empty());

        ResponseEntity response = deptoController.update(inputDto);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateExceptionTest() {
        DeptoDto inputDto = DeptoDto.builder()
                                    .nrDepto(77)
                                    .nmDepto("Erro")
                                    .dsLocal("Erro")
                                    .vlOrcamento(new BigDecimal("0"))
                                    .build();

        Mockito.when(deptoRepository.findByNrDepto(77)).thenThrow(new RuntimeException("Falha ao buscar"));

        ResponseEntity response = deptoController.update(inputDto);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void deleteSuccessTest() {
        Integer nrDepto = 10;

        Depto depto = Depto.builder()
                           .nrDepto(1)
                           .nrDepto(nrDepto)
                           .nmDepto("RH")
                           .build();

        when(deptoRepository.findByNrDepto(nrDepto)).thenReturn(Optional.of(depto));

        ResponseEntity response = deptoController.delete(nrDepto);

        assertEquals(OK, response.getStatusCode());
        verify(deptoRepository).deleteByNrDepto(nrDepto);
    }

    @Test
    public void deleteNotFoundTest() {
        Integer nrDepto = 99;

        when(deptoRepository.findByNrDepto(nrDepto)).thenReturn(Optional.empty());

        ResponseEntity response = deptoController.delete(nrDepto);

        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(deptoRepository, never()).deleteByNrDepto(anyInt());
    }

    @Test
    public void deleteExceptionTest() {
        Integer nrDepto = 77;

        Mockito.when(deptoRepository.findByNrDepto(nrDepto)).thenThrow(new RuntimeException("Erro inesperado"));

        ResponseEntity response = deptoController.delete(nrDepto);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
