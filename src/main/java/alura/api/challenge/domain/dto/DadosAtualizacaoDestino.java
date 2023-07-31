package alura.api.challenge.domain.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record DadosAtualizacaoDestino(
        @NotNull
        Long id,
        @URL
        String foto_url,
        String nome,
        BigDecimal preco
) {
}
