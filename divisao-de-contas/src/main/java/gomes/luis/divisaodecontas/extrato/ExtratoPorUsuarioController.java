package gomes.luis.divisaodecontas.extrato;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/extrato/usuario")
public class ExtratoPorUsuarioController {

    private final ExtratoPorUsuarioService extratoPorUsuarioService;
    private final ValorPorUsuarioToDTO converterToDTO;

    public ExtratoPorUsuarioController(ExtratoPorUsuarioService extratoPorUsuarioService, ValorPorUsuarioToDTO converterToDTO){
        this.extratoPorUsuarioService = extratoPorUsuarioService;
        this.converterToDTO = converterToDTO;
    }
    @GetMapping("/buscarValorPagoPorUsuarioNoPeriodo")
    public ResponseEntity<List<ValorPorUsuarioDTO>> buscarValorPagoPorUsuarioNoPeriodo(@RequestParam Long periodoId) {
        List<ValorPorUsuarioDTO> result = extratoPorUsuarioService.buscarValorPagoPorUsuarioNoPeriodo(periodoId)
                .stream()
                .map(converterToDTO::convert)
                .toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/buscarValorDevidoPorUsuarioNoPeriodo")
    public ResponseEntity<List<ValorPorUsuarioDTO>> buscarValorDevidoPorUsuarioNoPeriodo(@RequestParam Long periodoId) {
        List<ValorPorUsuarioDTO> result = extratoPorUsuarioService.buscarValorDevidoPorUsuarioNoPeriodo(periodoId)
                .stream()
                .map(converterToDTO::convert)
                .toList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
