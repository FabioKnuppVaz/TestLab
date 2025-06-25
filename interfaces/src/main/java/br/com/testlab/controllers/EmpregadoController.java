package br.com.testlab.controllers;

import br.com.testlab.dto.EmpregadoDto;
import br.com.testlab.usecases.empregado.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("empregado")
@Transactional
@Tag(name = "Empregados", description = "Gerenciamento de empregados")
public class EmpregadoController {

    @Autowired
    EmpregadoAtualizar empregadoAtualizar;

    @Autowired
    EmpregadoBuscar empregadoBuscar;

    @Autowired
    EmpregadoBuscarTodos empregadoBuscarTodos;

    @Autowired
    EmpregadoCadastrar empregadoCadastrar;

    @Autowired
    EmpregadoDeletar empregadoDeletar;

    @Operation(summary = "Buscar todos empregados")
    @GetMapping("findAll")
    public ResponseEntity findAll() {
        return empregadoBuscarTodos.findAll();
    }

    @Operation(summary = "Atualizar um empregado")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody EmpregadoDto empregadoDto) {
        return empregadoAtualizar.update(empregadoDto);
    }

    @Operation(summary = "Buscar um empregado")
    @GetMapping("read")
    public ResponseEntity read(@RequestParam Integer nrEmpregado) {
        return empregadoBuscar.read(nrEmpregado);
    }

    @Operation(summary = "Cadastrar um empregado")
    @PostMapping("create")
    public ResponseEntity create(@RequestBody EmpregadoDto empregadoDto) {
        return empregadoCadastrar.create(empregadoDto);
    }

    @Operation(summary = "Deletar um empregado")
    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestParam Integer nrEmpregado) {
        return empregadoDeletar.delete(nrEmpregado);
    }

}
