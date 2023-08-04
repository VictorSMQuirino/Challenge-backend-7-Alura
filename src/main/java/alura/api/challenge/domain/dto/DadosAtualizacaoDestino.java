package alura.api.challenge.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record DadosAtualizacaoDestino(
        @NotNull
        Long id,
        @URL
        String foto_url1,
        @URL
        String foto_url2,
        String nome,
        @Size(max = 160, message = "O campo meta deve ter no máximo 160 caracteres.")
        String meta,
        BigDecimal preco,
        String texto_descritivo
) {
}
