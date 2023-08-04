package alura.api.challenge.domain.dto;

import alura.api.challenge.domain.model.Destino;

import java.math.BigDecimal;

public record DadosDetalhamentoDestino(Long id, String foto_url1, String foto_url2, String nome, String meta,  BigDecimal preco, String texto_descritivo) {
    public DadosDetalhamentoDestino(Destino destino) {
        this(destino.getId(), destino.getFoto_url1(), destino.getFoto_url2(), destino.getNome(), destino.getMeta(), destino.getPreco(), destino.getTexto_descritivo());
    }
}
