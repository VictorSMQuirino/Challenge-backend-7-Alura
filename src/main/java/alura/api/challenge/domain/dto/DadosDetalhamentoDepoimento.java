package alura.api.challenge.domain.dto;

import alura.api.challenge.domain.model.Depoimento;

public record DadosDetalhamentoDepoimento(Long id, String comentario, String nome_autor, String foto_url) {
    public DadosDetalhamentoDepoimento(Depoimento depoimento) {
        this(depoimento.getId(), depoimento.getComentario(), depoimento.getNome_autor(), depoimento.getFoto_url());
    }
}
