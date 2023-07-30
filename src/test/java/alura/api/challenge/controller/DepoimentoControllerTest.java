package alura.api.challenge.controller;

import alura.api.challenge.domain.dto.DadosAtualizacaoDepoimento;
import alura.api.challenge.domain.dto.DadosCadastroDepoimento;
import alura.api.challenge.domain.dto.DadosDetalhamentoDepoimento;
import alura.api.challenge.domain.model.Depoimento;
import alura.api.challenge.repository.DepoimentoRepository;
import alura.api.challenge.service.DepoimentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DepoimentoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroDepoimento> cadastroDepoimentoJson;

    @Autowired
    private JacksonTester<DadosAtualizacaoDepoimento> atualizacaoDepoimentoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoDepoimento> detalhamentoDepoimentoJson;

    @MockBean
    private DepoimentoRepository depoimentoRepository;

    @MockBean
    private DepoimentoService depoimentoService;

    @Test
    @DisplayName("Deve retornar código 400 quando os dados de cadastro estáo inválidos.")
    public void cadastrar_cenario01() throws Exception {
        var response = mvc.perform(post("/depoimentos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar código 201 e json detalhado do depoimento quando os dados de cadastro estão válidos")
    public void cadastrar_cenario02() throws Exception {
        var comentario = "Comentario de teste de cadastro";
        var nome_autor = "nome de autor de teste de cadastro";
        var foto_url = "http://imagex.com/1234567.jpg";

        var jsonCadastro = new DadosCadastroDepoimento(comentario, nome_autor, foto_url);
        var jsonDetalhamento = new DadosDetalhamentoDepoimento(null, comentario, nome_autor, foto_url);

        when(depoimentoRepository.save(any())).thenReturn(new Depoimento(jsonCadastro));

        var response = mvc.perform(
                post("/depoimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cadastroDepoimentoJson.write(
                                jsonCadastro)
                                .getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        var jsonEsperado = detalhamentoDepoimentoJson.write(jsonDetalhamento).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar código 200 quando listar")
    public void listar() throws Exception {
        var response = mvc.perform(get("/depoimentos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve retornar código 200 quando buscar id válido")
    public void detalhar() throws Exception {
        var id = 1L;
        var comentario = "Comentario de busca";
        var nome_autor = "nome de autor de busca";
        var foto_url = "http://imagex.com/1234567.jpg";

        var jsonDetalhamento = new DadosDetalhamentoDepoimento(id, comentario, nome_autor, foto_url);
        when(depoimentoService.buscarPorId(id)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(jsonDetalhamento));

        var response = mvc.perform(get("/depoimentos/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = detalhamentoDepoimentoJson.write(jsonDetalhamento).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar código 400 quando editar sem passar o id")
    public void editar_cenario01() throws Exception {
        var response = mvc.perform(put("/depoimentos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar código 200 quando informações estão válidas")
    public void editar_cenario02() throws Exception {
        var comentario = "Comentario de atualizacao";
        var nome_autor = "nome de autor atualizacao";
        var foto_url = "http://imagex.com/1234567.jpg";

        var jsonAtualizacao = new DadosAtualizacaoDepoimento(1L, comentario, nome_autor, foto_url);
        var jsonDetalhamento = new DadosDetalhamentoDepoimento(1L, comentario, nome_autor, foto_url);

        when(depoimentoService.atualizar(any())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(jsonDetalhamento));

        var response = mvc.perform(
                put("/depoimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atualizacaoDepoimentoJson.write(
                                jsonAtualizacao)
                                .getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = detalhamentoDepoimentoJson.write(jsonDetalhamento).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar código 204 quando deletar")
    public void excluir() throws Exception {
        var id = 1L;
        var comentario = "Comentario de busca de exclusao";
        var nome_autor = "nome de autor de busca de exclusao";
        var foto_url = "http://imagex.com/1234567.jpg";
        var jsonDetalhamento = new DadosDetalhamentoDepoimento(id, comentario, nome_autor, foto_url);

        when(depoimentoService.excluir(any()))
                .thenReturn(ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .body(jsonDetalhamento));
        var response = mvc.perform(delete("/depoimentos/{id}", 1L)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        var jsonEsperado = detalhamentoDepoimentoJson.write(jsonDetalhamento).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}