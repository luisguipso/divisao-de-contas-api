package gomes.luis.divisaodecontas.extrato.categoria;

import gomes.luis.divisaodecontas.categoria.Categoria;

import java.math.BigDecimal;

public record ValorPorCategoriaDTO(Categoria categoria, BigDecimal valorTotal){}
