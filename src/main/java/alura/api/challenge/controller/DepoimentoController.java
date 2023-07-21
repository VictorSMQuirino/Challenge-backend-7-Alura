package alura.api.challenge.controller;

import alura.api.challenge.domain.dto.DadosAtualizacaoDepoimento;
import alura.api.challenge.domain.dto.DadosCadastroDepoimento;
import alura.api.challenge.domain.dto.DadosDetalhamentoDepoimento;
import alura.api.challenge.domain.model.Depoimento;
import alura.api.challenge.service.DepoimentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/depoimentos")
public class DepoimentoController {

    @Autowired
    private DepoimentoService depoimentoService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroDepoimento dados){
        return depoimentoService.salvar(dados);
    }

    @GetMapping
    public ResponseEntity<Page<Depoimento>> listar(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return depoimentoService.listar(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscar(@PathVariable Long id){
        return depoimentoService.buscarPorId(id);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoDepoimento dados){
        return depoimentoService.atualizar(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        return depoimentoService.excluir(id);
    }
}