package gomes.luis.divisaodecontas.periodo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PeriodoControler.class)
class PeriodoControllerTest {

    static final LocalDate DATA_5_5_2022 = LocalDate.of(2022, 5, 5);
    static final LocalDate DATA_1_5_2022 = LocalDate.of(2022, 5, 1);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PeriodoService periodoService;

    private Periodo maio;
    private ObjectMapper mapper;

    @BeforeEach
    private void setUp() {
        criarPeriodos();
        configurarJSONMapper();
    }

    private void criarPeriodos() {
        maio = new Periodo();
        maio.setId(1L);
    }

    private void configurarJSONMapper() {
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
    }

    @Test
    void buscarTodosOsPeriodosHappyDay_DeveRetornarSucesso() throws Exception {
        when(periodoService.buscarTodosOsPeriodos(1, 10))
                .thenReturn(List.of(maio));

        mockMvc.perform(get("/periodo"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarTodosOsPeriodosQuandoNaoExistem_DeveRetornarPeriodosNaoCadastrados() throws Exception {
        when(periodoService.buscarTodosOsPeriodos(1, 10))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/periodo")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").value("Recurso não encontrado."));
    }

    @Test
    void buscarPeriodoPorId_DeveRetornarOPeriodo() throws Exception {
        when(periodoService.buscarPorId(Long.valueOf(1)))
                .thenReturn(Optional.ofNullable(maio));

        String jsonMaio = mapper.writeValueAsString(maio);
        mockMvc.perform(get("/periodo/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(content().json(jsonMaio));
    }

    @Test
    void buscarPeriodoInexistente_DeveRetornarPeriodoNaoCadastrado() throws Exception {
        when(periodoService.buscarPorId(1L))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/periodo/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().encoding("UTF-8"))
                .andExpect(jsonPath("$.error").value("Recurso não encontrado."));
    }

    @Test
    void cadastrarPeriodoHappyDay_DeveRetornarIsCreated() throws Exception {
        when(periodoService.salvar(maio)).thenReturn(maio);

        String jsonMaio = mapper.writeValueAsString(maio);
        mockMvc.perform(post("/periodo")
                        .content(jsonMaio)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(PeriodoControler.PERIODO_CRIADO));
    }

    @Test
    void cadastrarPeriodoFaltandoCampos_DeveRetornarBadRequest() throws Exception {
        mockMvc.perform(post("/periodo")
                        .content("{something that isn't a periodo}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void alterarPeriodoHappyDay_DeveRetornarOk() throws Exception {
        Periodo periodoMockado = mock(Periodo.class);
        when(periodoService.atualizar(anyLong(), any(Periodo.class)))
                .thenReturn(periodoMockado);

        String jsonMaio = mapper.writeValueAsString(maio);
        mockMvc.perform(put("/periodo/{id}", 1)
                        .content(jsonMaio)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(PeriodoControler.PERIODO_ATUALIZADO));
    }

    @Test
    void alterarPeriodoInexistente_DeveRetornarNoContent() throws Exception {
        when(periodoService.atualizar(anyLong(), any(Periodo.class)))
                .thenThrow(new EntityNotFoundException("Entidade com id: " + 4L + ", não existe."));

        String jsonMaio = mapper.writeValueAsString(maio);
        mockMvc.perform(put("/periodo/{id}", 4)
                        .content(jsonMaio)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").value("Recurso não encontrado."));
    }

    @Test
    void apagarPeriodoHappyDay_DeveRetornarOk() throws Exception {
        doNothing().when(periodoService).excluirPorId(1L);

        mockMvc.perform(delete("/periodo/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(PeriodoControler.PERIODO_EXCLUIDO));
    }

    @Test
    void apagarPeriodoInexistente_DeveRetornarNoContent() throws Exception {
        doThrow(new EntityNotFoundException("Entidade com id: " + 4L + ", não existe."))
                .when(periodoService).excluirPorId(4L);

        mockMvc.perform(delete("/periodo/{id}", 4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.error").value("Recurso não encontrado."));
    }

}
