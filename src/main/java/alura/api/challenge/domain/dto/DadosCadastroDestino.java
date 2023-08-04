package alura.api.challenge.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

public record DadosCadastroDestino(
        @NotBlank(message = "Foto 1 do destino é obrigatória!")
        @URL
        String foto_url1,
        @NotBlank(message = "Foto 2 do destino é obrigatória!")
        @URL
        String foto_url2,
        @NotBlank(message = "Nome do destino é obrigatório!")
        String nome,
        @NotBlank(message = "Meta do destino é obrigatória!")
        @Size(max = 160, message = "O campo meta deve ter no máximo 160 caracteres.")
        String meta,
        String texto_descritivo,
        @NotNull(message = "Preço da viagem é obrigatório!")
        BigDecimal preco
) {
}
