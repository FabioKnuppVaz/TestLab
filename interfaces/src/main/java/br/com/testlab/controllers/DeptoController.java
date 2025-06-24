package br.com.testlab.controllers;

import br.com.testlab.dto.DeptoDto;
import br.com.testlab.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("depto")
@Transactional
@Tag(name = "Departamentos", description = "Gerenciamento de departamentos")
public class DeptoController {

    @Autowired
    DeptoAtualizar deptoAtualizar;

    @Autowired
    DeptoBuscar deptoBuscar;

    @Autowired
    DeptoBuscarTodos deptoBuscarTodos;

    @Autowired
    DeptoCadastrar deptoCadastrar;

    @Autowired
    DeptoDeletar deptoDeletar;

    @Operation(summary = "Buscar todos departamentos")
    @GetMapping("findAll")
    public ResponseEntity findAll(){
        return deptoBuscarTodos.findAll();
    }

    @Operation(summary = "Atualizar um departamento")
    @PutMapping("update")
    public ResponseEntity update(@RequestBody DeptoDto deptoDto) {
        return deptoAtualizar.update(deptoDto);
    }

    @Operation(summary = "Buscar um departamento")
    @GetMapping("read")
    public ResponseEntity read(@RequestParam Integer nrDepto) {
        return deptoBuscar.read(nrDepto);
    }

    @Operation(summary = "Cadastrar um departamento")
    @PostMapping("create")
    public ResponseEntity create(@RequestBody DeptoDto deptoDto){
        return deptoCadastrar.create(deptoDto);
    }

    @Operation(summary = "Deletar um departamento")
    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestParam Integer nrDepto) {
        return deptoDeletar.delete(nrDepto);
    }

}
