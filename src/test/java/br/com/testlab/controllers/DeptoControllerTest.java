package br.com.testlab.controllers;

import br.com.testlab.dtos.DeptoDto;
import br.com.testlab.services.DeptoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeptoController.class)
class DeptoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeptoService deptoService;

    @Autowired
    private ObjectMapper objectMapper;

    private DeptoDto sampleDto;

    @BeforeEach
    void setUp() {
        sampleDto = new DeptoDto();
        sampleDto.setNrDepto(1);
        sampleDto.setNmDepto("TI");
    }

    @Test
    void testFindAll_ReturnsOk() throws Exception {
        when(deptoService.findAll()).thenReturn(List.of(sampleDto));

        mockMvc.perform(get("/depto/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testFindAll_ReturnsNotFound() throws Exception {
        when(deptoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/depto/findAll"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindById_ReturnsOk() throws Exception {
        when(deptoService.findById(1)).thenReturn(sampleDto);

        mockMvc.perform(get("/depto/findById")
                        .param("nrDepto", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nrDepto").value(1));
    }

    @Test
    void testFindById_ReturnsNotFound() throws Exception {
        when(deptoService.findById(1)).thenReturn(null);

        mockMvc.perform(get("/depto/findById")
                        .param("nrDepto", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteById_ReturnsAccepted() throws Exception {
        doNothing().when(deptoService).deleteById(1);

        mockMvc.perform(delete("/depto/deleteById")
                        .param("nrDepto", "1"))
                .andExpect(status().isAccepted());
    }

    @Test
    void testReplaceById_WhenExists() throws Exception {
        when(deptoService.findById(1)).thenReturn(sampleDto);
        when(deptoService.replaceById(sampleDto)).thenReturn(sampleDto);

        mockMvc.perform(patch("/depto/replaceById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nrDepto").value(1));
    }

    @Test
    void testReplaceById_WhenNotFound() throws Exception {
        when(deptoService.findById(1)).thenReturn(null);

        mockMvc.perform(patch("/depto/replaceById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isNotFound());
    }
    @Disabled
    @Test
    void testInsert_ReturnsCreated() throws Exception {
        doNothing().when(deptoService).insert(sampleDto);

        mockMvc.perform(put("/depto/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isCreated());
    }
}
