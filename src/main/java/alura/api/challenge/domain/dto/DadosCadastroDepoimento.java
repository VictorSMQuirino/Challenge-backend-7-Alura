package alura.api.challenge.domain.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record DadosCadastroDepoimento(
        @NotBlank(message = "O comentário é obrigatório!")
        String comentario,
        @NotBlank(message = "O nome do autor do depoimento é obrigatório!")
        String nome_autor,
        @URL
        String foto_url
) {
}
