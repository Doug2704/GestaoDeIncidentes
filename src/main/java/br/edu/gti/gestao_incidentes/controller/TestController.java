package br.edu.gti.gestao_incidentes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/mensagem")
    public ResponseEntity<?> findById() {
        return new ResponseEntity<>("Olá mundo! \n\nEsse é apenas um teste de requisição HTTP." +
                "\nSe você está vendo essa mensagem, então a integração do front-end com o back-end teve sucesso!", HttpStatus.OK);
    }
}
