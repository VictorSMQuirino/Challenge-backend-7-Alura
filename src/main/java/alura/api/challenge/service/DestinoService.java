package alura.api.challenge.service;

import alura.api.challenge.domain.dto.DadosAtualizacaoDestino;
import alura.api.challenge.domain.dto.DadosCadastroDestino;
import alura.api.challenge.domain.dto.DadosDetalhamentoDestino;
import alura.api.challenge.domain.model.Destino;
import alura.api.challenge.infra.exception.BuscaVaziaException;
import alura.api.challenge.infra.exception.RegistroInativoException;
import alura.api.challenge.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DestinoService {

    @Autowired
    private DestinoRepository destinoRepository;

    @Autowired
    private GeraTextoDescritivoService geraTextoDescritivoService;

    public ResponseEntity salvar(DadosCadastroDestino dados) {
        String texto = dados.texto_descritivo();
        if(texto.isBlank()){
            texto = geraTextoDescritivoService.geraTextoDescritivo(dados.nome());
        }
        var destino =  new Destino(dados, texto);
        destinoRepository.save(destino);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoDestino(destino));
    }

    public ResponseEntity<Page<Destino>> listar(Pageable page, String busca) {
        if(busca != null){
            var destinos = destinoRepository.findAllByNomeContainingAndAtivoTrue(busca, page);
            if(destinos.isEmpty()){
                throw new BuscaVaziaException("Nenhum destino encontrado.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(destinos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(destinoRepository.findAllByAtivoTrue(page));
    }

    public ResponseEntity buscarPorId(Long id) {
        var destino = destinoRepository.getReferenceByIdAndAtivoTrue(id);
        if(destino == null){
            throw new RegistroInativoException("Destino não encontrado!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoDestino(destino));
    }

    public ResponseEntity atualizar(DadosAtualizacaoDestino dados) {
        var destino = destinoRepository.getReferenceByIdAndAtivoTrue(dados.id());
        if(destino == null){
            throw new RegistroInativoException("Destino não encontrado!");
        }
        destino.atualizar(dados);

        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoDestino(destino));
    }

    public ResponseEntity excluir(Long id) {
        var destino = destinoRepository.getReferenceByIdAndAtivoTrue(id);
        if(destino == null){
            throw new RegistroInativoException("Destino não encontrado!");
        }
        destino.excluir();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new DadosDetalhamentoDestino(destino));
    }
}
