package gomes.luis.divisaodecontas.extrato;

import gomes.luis.divisaodecontas.pessoa.Pessoa;
import java.math.BigDecimal;

public record ValorPorUsuarioDTO (Pessoa usuario, BigDecimal valorTotal){}
