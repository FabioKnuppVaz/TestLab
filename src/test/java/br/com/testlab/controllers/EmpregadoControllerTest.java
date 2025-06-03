package br.com.testlab.controllers;

import br.com.testlab.dtos.EmpregadoDto;
import br.com.testlab.services.EmpregadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpregadoController.class)
class EmpregadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpregadoService empregadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll_Success() throws Exception {
        EmpregadoDto dto = new EmpregadoDto();
        dto.setNrEmpregado(1);
        dto.setNmEmpregado("João");

        when(empregadoService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/empregado/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nrEmpregado").value(1))
                .andExpect(jsonPath("$[0].nmEmpregado").value("João"));
    }

    @Test
    void testGetAll_NotFound() throws Exception {
        when(empregadoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/empregado/all"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindById_Success() throws Exception {
        EmpregadoDto dto = new EmpregadoDto();
        dto.setNrEmpregado(1);
        dto.setNmEmpregado("Maria");

        when(empregadoService.findById(1)).thenReturn(dto);

        mockMvc.perform(get("/empregado/findById")
                        .param("nrEmpregado", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nrEmpregado").value(1))
                .andExpect(jsonPath("$.nmEmpregado").value("Maria"));
    }

    @Test
    void testFindById_NotFound() throws Exception {
        when(empregadoService.findById(1)).thenReturn(null);

        mockMvc.perform(get("/empregado/findById")
                        .param("nrEmpregado", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteById_Success() throws Exception {
        doNothing().when(empregadoService).deleteById(1);

        mockMvc.perform(delete("/empregado/deleteById")
                        .param("nrEmpregado", "1"))
                .andExpect(status().isAccepted());

        verify(empregadoService, times(1)).deleteById(1);
    }

    @Disabled
    @Test
    void testReplaceById_Success() throws Exception {
        EmpregadoDto dto = new EmpregadoDto();
        dto.setNrEmpregado(1);
        dto.setNmEmpregado("Carlos");

        when(empregadoService.findById(1)).thenReturn(dto);
        doNothing().when(empregadoService).replaceById(any(EmpregadoDto.class));

        mockMvc.perform(patch("/empregado/replaceById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nrEmpregado").value(1))
                .andExpect(jsonPath("$.nmEmpregado").value("Carlos"));
    }

    @Test
    void testReplaceById_NotFound() throws Exception {
        EmpregadoDto dto = new EmpregadoDto();
        dto.setNrEmpregado(1);

        when(empregadoService.findById(1)).thenReturn(null);

        mockMvc.perform(patch("/empregado/replaceById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Disabled
    @Test
    void testInsert_Success() throws Exception {
        EmpregadoDto dto = new EmpregadoDto();
        dto.setNrEmpregado(2);
        dto.setNmEmpregado("Ana");

        doNothing().when(empregadoService).insert(any(EmpregadoDto.class));

        mockMvc.perform(put("/empregado/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nrEmpregado").value(2))
                .andExpect(jsonPath("$.nmEmpregado").value("Ana"));
    }
}
