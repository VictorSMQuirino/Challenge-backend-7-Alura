package alura.api.challenge.domain.dto;

import alura.api.challenge.domain.model.Destino;

import java.math.BigDecimal;

public record DadosDetalhamentoDestino(Long id, String foto_url, String nome, BigDecimal preco) {
    public DadosDetalhamentoDestino(Destino destino) {
        this(destino.getId(), destino.getFoto_url(), destino.getNome(), destino.getPreco());
    }
}
