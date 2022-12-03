package gomes.luis.divisaodecontas.services;

import gomes.luis.divisaodecontas.models.Despesa;
import gomes.luis.divisaodecontas.models.Pagamento;
import gomes.luis.divisaodecontas.models.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static gomes.luis.divisaodecontas.services.util.TestUtils.criarDespesa;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {
    @InjectMocks
    PagamentoService pagamentoService;
    Despesa despesa;
    PagamentoService spiedPagamentoService;

    @BeforeEach
    public void setUp(){
        spiedPagamentoService = spy(pagamentoService);
        despesa = criarDespesa(BigDecimal.valueOf(100));
    }

    @Test
    public void calcularPagamentos_DeveRetornarPagamentos(){
        Despesa spiedDespesa = spy(despesa);
        doReturn(List.of(mock(Pessoa.class),mock(Pessoa.class)))
                .when(spiedDespesa).getPagadores();

        List<Pagamento> pagamentos = spiedPagamentoService.gerarPagamentos(spiedDespesa);

        assertNotNull(pagamentos,"Pagamentos da despesa.");
        assertFalse(pagamentos.isEmpty(),"Pagamentos da despesa.");
    }

    @Test
    public void calcularPagamentos_DeveRetornarPagamentosComValorTotalIgualAoDaDespesa(){
        Despesa spiedDespesa = spy(despesa);
        doReturn(List.of(mock(Pessoa.class),mock(Pessoa.class)))
                .when(spiedDespesa).getPagadores();
        BigDecimal valorTotalPagamentosGerados = new BigDecimal(0);
        BigDecimal valorDaDespesa = BigDecimal.valueOf(100);
        assertEquals(valorDaDespesa,spiedDespesa.getValor(), "Valor da despesa.");

        List<Pagamento> pagamentosGerados = spiedPagamentoService.gerarPagamentos(spiedDespesa);

        for(Pagamento cada : pagamentosGerados){
            valorTotalPagamentosGerados = valorTotalPagamentosGerados.add(cada.getValor());
        }
        assertEquals(valorDaDespesa, valorTotalPagamentosGerados, "Valor total dos pagamentos.");
    }

    @Test
    public void calcularPagamentosAoDividirDespesaParaDuasPessoas_DeveCriarPagamentosComOValorProporcional(){
        Despesa spiedDespesa = spy(despesa);
        doReturn(List.of(mock(Pessoa.class),mock(Pessoa.class)))
                .when(spiedDespesa).getPagadores();
        assertEquals(BigDecimal.valueOf(100),spiedDespesa.getValor(), "Valor da despesa.");

        List<Pagamento> pagamentos = spiedPagamentoService.gerarPagamentos(spiedDespesa);

        BigDecimal valorExperado = BigDecimal.valueOf(50);
        assertEquals(2,pagamentos.size(),"Quantidade de pagamentos");
        assertEquals(valorExperado, pagamentos.get(0).getValor(), "Valor de um pagamento.");
        assertEquals(valorExperado, pagamentos.get(1).getValor(), "Valor de outro pagamento.");

    }

    @Test
    public void calcularPagamentosParaDespesaNaoDivisivel_DeveCriarUmPagamentoComOValorTotalDaDespesa(){
        Despesa despesaNaoDivisivel = getSpiedDespesaNaoDivisivel();
        BigDecimal valorTotalDaDespesa = BigDecimal.valueOf(100);
        assertEquals(valorTotalDaDespesa, despesaNaoDivisivel.getValor(), "Valor da despesa não divisivel.");

        List<Pagamento> pagamentos = spiedPagamentoService.gerarPagamentos(despesaNaoDivisivel);
        BigDecimal valorTotalDosPagamentos = pagamentos.stream()
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, (a,b) -> a.add(b));

        assertEquals(valorTotalDaDespesa, valorTotalDosPagamentos, "Valor de um pagamento.");
        assertEquals(1,pagamentos.size(),"Quantidade de pagamentos");
    }

    private Despesa getSpiedDespesaNaoDivisivel() {
        Despesa despesaNaoDivisivel = spy(despesa);
        despesaNaoDivisivel.setDivisivel(false);
        return despesaNaoDivisivel;
    }
}
