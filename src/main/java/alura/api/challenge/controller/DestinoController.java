package alura.api.challenge.controller;

import alura.api.challenge.domain.dto.DadosAtualizacaoDestino;
import alura.api.challenge.domain.dto.DadosCadastroDestino;
import alura.api.challenge.service.DestinoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

    @Autowired
    private DestinoService destinoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroDestino dados){
        return destinoService.salvar(dados);
    }

    @GetMapping
    public ResponseEntity listar(@PageableDefault(page = 0, size = 10) Pageable page, @RequestParam(name = "nome", required = false) String busca){
        return destinoService.listar(page, busca);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        return destinoService.buscarPorId(id);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoDestino dados){
        return destinoService.atualizar(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        return destinoService.excluir(id);
    }
}
