package gomes.luis.divisaodecontas.extrato.categoria;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/extrato/categoria")
public class ExtratoPorCategoriaController {

    private final ExtratoPorCategoriaService extratoPorCategoriaService;
    private final ValorPorCategoriaToDTO converterToDTO;

    public ExtratoPorCategoriaController(ExtratoPorCategoriaService extratoPorCategoriaService, ValorPorCategoriaToDTO converterToDTO){
        this.extratoPorCategoriaService = extratoPorCategoriaService;
        this.converterToDTO = converterToDTO;
    }

    @GetMapping("/buscarValorTotalPorCategoriaEUsuarioNoPeriodo")
    public ResponseEntity<List<ValorPorCategoriaDTO>> buscarValorTotalPorCategoriaEUsuarioNoPeriodo(@RequestParam Long periodoId, @RequestParam Long usuarioId){
        List<ValorPorCategoriaDTO> result = extratoPorCategoriaService.buscarValorTotalPorCategoriaEUsuarioNoPeriodo(periodoId, usuarioId)
                .stream()
                .map(converterToDTO::convert)
                .toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
