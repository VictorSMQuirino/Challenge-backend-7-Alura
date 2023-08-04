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
    private String foto_url1;
    private String foto_url2;
    private String nome;
    private String meta;
    private BigDecimal preco;
    private String texto_descritivo;
    private Boolean ativo;

    public Destino(DadosCadastroDestino dados) {
        this.foto_url1 = dados.foto_url1();
        this.foto_url2 = dados.foto_url2();
        this.nome = dados.nome();
        this.meta = dados.meta();
        this.preco = dados.preco();
        this.texto_descritivo = dados.texto_descritivo();
        this.ativo = true;
    }

    public void atualizar(DadosAtualizacaoDestino dados) {
        if(dados.foto_url1() != null){
            this.foto_url1 = dados.foto_url1();
        }
        if(dados.foto_url2() != null){
            this.foto_url1 = dados.foto_url2();
        }
        if(dados.nome() != null){
            this.nome = dados.nome();
        }
        if(dados.meta() != null){
           this.meta = dados.meta();
        }
        if(dados.preco() != null){
            this.preco = dados.preco();
        }
        if(dados.texto_descritivo() != null){
            this.texto_descritivo = dados.texto_descritivo();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
