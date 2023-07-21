package alura.api.challenge.service;

import alura.api.challenge.domain.dto.DadosAtualizacaoDepoimento;
import alura.api.challenge.domain.dto.DadosCadastroDepoimento;
import alura.api.challenge.domain.dto.DadosDetalhamentoDepoimento;
import alura.api.challenge.domain.model.Depoimento;
import alura.api.challenge.repository.DepoimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepoimentoService {

    @Autowired
    private DepoimentoRepository depoimentoRepository;

    public ResponseEntity salvar(DadosCadastroDepoimento dados){
        var depoimento = new Depoimento(dados);
        depoimentoRepository.save(depoimento);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoDepoimento(depoimento));
    }

    public ResponseEntity<Page<Depoimento>> listar(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(depoimentoRepository.findAllByAtivoTrue(pageable));
    }

    public ResponseEntity buscarPorId(Long id) {
        if(!depoimentoRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Depoimento não encontrado!");
        }
        var depoimento = depoimentoRepository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoDepoimento(depoimento));
    }

    public ResponseEntity atualizar(DadosAtualizacaoDepoimento dados) {
        if(!depoimentoRepository.existsById(dados.id())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Depoimento não encontrado!");
        }
        var depoimento = depoimentoRepository.getReferenceById(dados.id());

        depoimento.atualizar(dados);
        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoDepoimento(depoimento));
    }

    public ResponseEntity excluir(Long id) {
        if(!depoimentoRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Depoimento não encontrado!");
        }
        var depoimento = depoimentoRepository.getReferenceById(id);

        depoimento.excluir();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DadosDetalhamentoDepoimento(depoimento));
    }

    public ResponseEntity<List<Depoimento>> buscarTresAleatorios() {
        return ResponseEntity.status(HttpStatus.OK).body(depoimentoRepository.buscarDepoimentosAleatorios());
    }
}