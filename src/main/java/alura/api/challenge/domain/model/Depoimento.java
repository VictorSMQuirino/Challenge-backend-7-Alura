package alura.api.challenge.domain.model;

import alura.api.challenge.domain.dto.DadosAtualizacaoDepoimento;
import alura.api.challenge.domain.dto.DadosCadastroDepoimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Depoimento")
@Table(name = "depoimentos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Depoimento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comentario;
    private String nome_autor;
    private String foto_url;
    private Boolean ativo;

    public Depoimento(DadosCadastroDepoimento dados) {
        this.comentario = dados.comentario();
        this.nome_autor = dados.nome_autor();
        this.foto_url = dados.foto_url();
        this.ativo = true;
    }

    public void atualizar(DadosAtualizacaoDepoimento dados) {
        if(dados.comentario() != null){
            this.comentario = dados.comentario();
        }
        if(dados.nome_autor() != null){
            this.nome_autor = dados.nome_autor();
        }
        if(dados.foto_url() != null){
            this.foto_url = dados.foto_url();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}