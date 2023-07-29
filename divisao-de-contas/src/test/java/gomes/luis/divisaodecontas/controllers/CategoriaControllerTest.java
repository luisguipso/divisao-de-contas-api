package gomes.luis.divisaodecontas.controllers;

import gomes.luis.divisaodecontas.categoria.Categoria;
import gomes.luis.divisaodecontas.categoria.CategoriaController;
import gomes.luis.divisaodecontas.categoria.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoriaController.class)
public class CategoriaControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CategoriaService categoriaService;
    Categoria categoriaMercado;

    @BeforeEach
    public void setUp() {
        categoriaMercado = new Categoria("Mercado");
    }

    @Test
    public void buscarCategoriaPorId_DeveRetornarOk() throws Exception {
        when(categoriaService.buscarPorId(Long.valueOf(1)))
                .thenReturn(Optional.ofNullable(categoriaMercado));

        mockMvc.perform(get("/categoria/{id}", Long.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void buscarCategoriaInexistentePorId_DeveRetornarNoContent() throws Exception {
        when(categoriaService.buscarPorId(2l))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/categoria/{id}", 2l))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").value("Recurso não encontrado."));
    }
}
