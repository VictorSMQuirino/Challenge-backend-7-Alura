package alura.api.challenge.controller;

import alura.api.challenge.domain.model.Depoimento;
import alura.api.challenge.service.DepoimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/depoimentos-home")
public class DepoimentoHomeController {

    @Autowired
    private DepoimentoService depoimentoService;

    @GetMapping
    public ResponseEntity<List<Depoimento>> buscarTresDepoimentosAleatorios(){
        return depoimentoService.buscarTresAleatorios();
    }
}
