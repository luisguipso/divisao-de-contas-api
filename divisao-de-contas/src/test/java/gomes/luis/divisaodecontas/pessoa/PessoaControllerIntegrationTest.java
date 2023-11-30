package gomes.luis.divisaodecontas.pessoa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
public class PessoaControllerIntegrationTest {
    public static final String PESSOA_PATH = "/api/v1/pessoa";
    @Autowired
    private MockMvc mockMvc;
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
        Pessoa pessoa = new Pessoa("Luis", 0);

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
        criarPessoaViaRequest(new Pessoa("Luis", 0));
        criarPessoaViaRequest(new Pessoa("Cyntia", 0));

        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_PATH))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].nome").value("Luis"))
                .andExpect(jsonPath("$[1].nome").value("Cyntia"));
    }

    @Test
    @Transactional
    public void alterarPessoa_RetornaPessoasCorretamente() throws Exception {
        criarPessoaViaRequest(new Pessoa("Luis", 0));
        criarPessoaViaRequest(new Pessoa("Cyntia", 0));

        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_PATH))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].nome").value("Luis"))
                .andExpect(jsonPath("$[1].nome").value("Cyntia"));

        Optional<Pessoa> pessoa = pessoaRepository.findByNome("Luis");
        Pessoa alterada = pessoa.orElseThrow();
        alterada.setNome("Joao");

        String caminhoUpdatePessoa1 = PESSOA_PATH + "/" + alterada.getId().toString();
        String jsonAlterada = mapper.writeValueAsString(alterada);
        mockMvc.perform(MockMvcRequestBuilders.put(caminhoUpdatePessoa1)
                        .content(jsonAlterada)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.nome").value("Joao"));

        mockMvc.perform(MockMvcRequestBuilders.get(PESSOA_PATH))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].nome").value("Joao"))
                .andExpect(jsonPath("$[1].nome").value("Cyntia"));

    }

    private void criarPessoaViaRequest(Pessoa pessoa) throws Exception {
        String json = mapper.writeValueAsString(pessoa);
        mockMvc.perform(MockMvcRequestBuilders.post(PESSOA_PATH)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
