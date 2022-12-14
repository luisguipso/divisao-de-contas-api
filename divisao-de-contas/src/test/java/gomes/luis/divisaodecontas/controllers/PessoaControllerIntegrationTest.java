package gomes.luis.divisaodecontas.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gomes.luis.divisaodecontas.DivisaoDeContasApplicationTests;
import gomes.luis.divisaodecontas.models.Pessoa;
import gomes.luis.divisaodecontas.repositories.PessoaRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@AutoConfigureMockMvc
public class PessoaControllerIntegrationTest extends DivisaoDeContasApplicationTests {
    public static final String PESSOA_PATH = "/pessoa";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PessoaController pessoaController;
    @Autowired
    private PessoaRepository pessoaRepository;
    ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @Transactional
    public void criarPessoaHappyDay_RetornaIsCreated() throws Exception {
        Pessoa pessoa = new Pessoa("Luis");


        String json = mapper.writeValueAsString(pessoa);
        mockMvc.perform(MockMvcRequestBuilders.post(PESSOA_PATH)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @Transactional
    public void buscarTodasAsPessoasHappyDay_RetornaPessoas() throws Exception {
        criarPessoaViaRequest(new Pessoa("Luis"));
        criarPessoaViaRequest(new Pessoa("Cyntia"));

        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_PATH))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Luis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Cyntia"));
    }

    @Test
    @Transactional
    public void alterarPessoa_RetornaPessoasCorretamente() throws Exception {
        criarPessoaViaRequest(new Pessoa("Luis"));
        criarPessoaViaRequest(new Pessoa("Cyntia"));

        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_PATH))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Luis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Cyntia"));

        List<Pessoa> pessoas = pessoaRepository.findByNome("Luis");
        Pessoa alterada = pessoas.get(0);
        alterada.setNome("Joao");

        String caminhoUpdatePessoa1 = PESSOA_PATH + "/" + alterada.getId().toString();
        String jsonAlterada = mapper.writeValueAsString(alterada);
        mockMvc.perform(MockMvcRequestBuilders.put(caminhoUpdatePessoa1)
                        .content(jsonAlterada)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Joao"));

        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_PATH))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Joao"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Cyntia"));

    }

    private Pessoa criarPessoaViaRequest(Pessoa pessoa) throws Exception {
        String json = mapper.writeValueAsString(pessoa);
        mockMvc.perform(MockMvcRequestBuilders.post(PESSOA_PATH)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
        return pessoa;
    }
}
