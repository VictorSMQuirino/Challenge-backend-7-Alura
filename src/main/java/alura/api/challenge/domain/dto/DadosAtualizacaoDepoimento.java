package alura.api.challenge.domain.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record DadosAtualizacaoDepoimento(
        @NotNull
        Long id,
        String comentario,
        String nome_autor,
        @URL
        String foto_url) {
}
