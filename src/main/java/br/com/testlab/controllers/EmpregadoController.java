package br.com.testlab.controllers;

import br.com.testlab.models.Empregado;
import br.com.testlab.repositories.EmpregadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("empregado")
public class EmpregadoController {

    @Autowired
    EmpregadoRepository empregadoRepository;

    @GetMapping("all")
    public List<Empregado> getAll() {
        return empregadoRepository
               .findAll();
    }

}
