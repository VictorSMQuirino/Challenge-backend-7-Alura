package alura.api.challenge.domain.model;

import alura.api.challenge.domain.dto.DadosAtualizacaoDestino;
import alura.api.challenge.domain.dto.DadosCadastroDestino;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "Destino")
@Table(name = "destinos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Destino {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String foto_url;
    private String nome;
    private BigDecimal preco;
    private Boolean ativo;

    public Destino(DadosCadastroDestino dados) {
        this.foto_url = dados.foto_url();
        this.nome = dados.nome();
        this.preco = dados.preco();
        this.ativo = true;
    }

    public void atualizar(DadosAtualizacaoDestino dados) {
        if(dados.foto_url() != null){
            this.foto_url = dados.foto_url();
        }
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.preco() != null){
            this.preco = dados.preco();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
