package alura.api.challenge.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record DadosCadastroDestino(
        @NotBlank(message = "Foto do destino é obrigatória!")
        @URL
        String foto_url,
        @NotBlank(message = "Nome do destino é obrigatório!")
        String nome,
        @NotNull(message = "Preço da viagem é obrigatório!")
        BigDecimal preco
) {
}
