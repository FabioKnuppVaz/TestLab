package br.com.testlab.usecases.depto;

import br.com.testlab.dto.DeptoDto;
import br.com.testlab.entities.Depto;
import br.com.testlab.persistence.jpa.DeptoRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeptoAtualizarTest {

    @Mock
    private DeptoRepository deptoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DeptoAtualizar deptoAtualizar;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateDeptoSucessoTest() {
        Depto deptoExistente = Depto.builder()
                                    .nrDepto(1)
                                    .nmDepto("TI")
                                    .dsLocal("Andar 2")
                                    .vlOrcamento(new BigDecimal("1000"))
                                    .build();

        DeptoDto dto = new DeptoDto();
        dto.setNrDepto(1);
        dto.setNmDepto("Tecnologia");
        dto.setDsLocal("Andar 5");
        dto.setVlOrcamento(new BigDecimal("2000"));

        Depto mapeado = Depto.builder()
                            .nrDepto(1)
                            .nmDepto("Tecnologia")
                            .dsLocal("Andar 5")
                            .vlOrcamento(new BigDecimal("2000"))
                            .build();

        when(deptoRepository.findByNrDepto(1)).thenReturn(Optional.of(deptoExistente));
        when(modelMapper.map(deptoExistente, Depto.class)).thenReturn(mapeado);

        ResponseEntity response = deptoAtualizar.update(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
        verify(deptoRepository).save(mapeado);
    }

    @Test
    public void updateDeptoNotFoundTest() {
        DeptoDto dto = new DeptoDto();
        dto.setNrDepto(999);

        when(deptoRepository.findByNrDepto(999)).thenReturn(Optional.empty());

        ResponseEntity response = deptoAtualizar.update(dto);

        assertEquals(404, response.getStatusCodeValue());
        verify(deptoRepository, never()).save(any());
    }

    @Test
    public void updateExceptionTest() {
        DeptoDto dto = new DeptoDto();
        dto.setNrDepto(1);

        when(deptoRepository.findByNrDepto(1)).thenThrow(new RuntimeException("Erro interno"));

        ResponseEntity response = deptoAtualizar.update(dto);

        assertEquals(500, response.getStatusCodeValue());
    }

}
