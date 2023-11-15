package gomes.luis.divisaodecontas.controllers;

import gomes.luis.divisaodecontas.pessoa.Pessoa;
import gomes.luis.divisaodecontas.pessoa.PessoaController;
import gomes.luis.divisaodecontas.pessoa.PessoaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PessoaController.class)
class PessoaControllerTest {

    @Autowired
    MockMvc mockMVC;

    @MockBean
    private PessoaService pessoaService;

    @Test
    void buscarPessoaExistente_DeveRetornarOk() throws Exception {
        when(pessoaService.buscarTodos())
                .thenReturn(List.of(new Pessoa("LUIS", 55)));
        mockMVC.perform(MockMvcRequestBuilders.get("/pessoa"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
