package br.com.testlab.controllers;

import br.com.testlab.models.Depto;
import br.com.testlab.repositories.DeptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("depto")
public class DeptoController {

    @Autowired
    DeptoRepository deptoRepository;

    @GetMapping("all")
    public List<Depto> getAll() {
        return deptoRepository
               .findAll();
    }

}
