package alura.api.challenge.controller;

import alura.api.challenge.domain.dto.DadosAtualizacaoDestino;
import alura.api.challenge.domain.dto.DadosCadastroDestino;
import alura.api.challenge.domain.dto.DadosDetalhamentoDestino;
import alura.api.challenge.domain.model.Destino;
import alura.api.challenge.repository.DestinoRepository;
import alura.api.challenge.service.DestinoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class DestinoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroDestino> cadastroDestinoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoDestino> detalhamentoDestinoJson;

    @Autowired
    private JacksonTester<DadosAtualizacaoDestino> atualizacaoDestinoJson;

    @MockBean
    private DestinoRepository destinoRepository;

    @MockBean
    private DestinoService destinoService;

    @Test
    @DisplayName("Deve retornar código 400 quando os dados de cadastro estão inválidos")
    public void cadastrar_cenario01() throws Exception {
        var response = mvc.perform(post("/destinos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar código 201 quando os dados de cadastro estão válidos")
    public void cadastrar_cenario02() throws Exception {
        var foto_url = "http://imagex/0014563.jpg";
        var nome = "Londres";
        var preco = new BigDecimal(800.00);

        var jsonCadastro = new DadosCadastroDestino(foto_url, nome, preco);
        var jsonDetalhamento = new DadosDetalhamentoDestino(null, foto_url, nome, preco);

        when(destinoRepository.save(any())).thenReturn(new Destino(jsonCadastro));

        var response = mvc.perform(
                post("/destinos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cadastroDestinoJson.write(
                                jsonCadastro)
                                .getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        var jsonEsperado = detalhamentoDestinoJson.write(jsonDetalhamento).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar código 200 quando listar")
    public void listar() throws Exception {
        var response = mvc.perform(get("/destinos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve retornar código 404 quando a busca não encontrar nenhum resultado")
    public void buscar_cenario01() throws Exception {
        var busca = "Paris";

        when(destinoRepository.findAllByNomeContainingAndAtivoTrue(any(), any())).thenReturn(Page.empty());

        var response = mvc.perform(
                get("/destinos")
                        .param("nome", busca)
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Deve retornar código 400 quando editar sem passar o id")
    public void editar_cenario01() throws Exception {
        var response = mvc.perform(put("/destinos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar código 200 quando as informações estão válidas")
    public void editar_cenario02() throws Exception {
        var foto_url = "http://imagex.com/1234567.jpg";
        var nome = "Paris";
        var preco = new BigDecimal(500.00);

        var jsonAtualizacao = new DadosAtualizacaoDestino(1L, foto_url, nome, preco);
        var jsonDetalhamento = new DadosDetalhamentoDestino(1L, foto_url, nome, preco);

        when(destinoService.atualizar(any())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(jsonDetalhamento));

        var response = mvc.perform(
                put("/destinos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atualizacaoDestinoJson.write(jsonAtualizacao).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = detalhamentoDestinoJson.write(jsonDetalhamento).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deve retornar código 204 quando deletar")
    public void excluir() throws Exception {
        var id = 1L;
        var foto_url = "http://imagex.com/1234567.jpg";
        var nome = "Paris";
        var preco = new BigDecimal(500.00);
        var jsonDetalhamento = new DadosDetalhamentoDestino(id, foto_url, nome, preco);

        when(destinoService.excluir(id)).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).body(jsonDetalhamento));

        var response = mvc.perform(delete("/destinos/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        var jsonEsperado = detalhamentoDestinoJson.write(jsonDetalhamento).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}