
package br.com.ordensservico.controller;

import br.com.ordensservico.dto.OrdemServicoRequest;
import br.com.ordensservico.model.OrdemServico;
import br.com.ordensservico.service.OrdemServicoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoService service;

    public OrdemServicoController(OrdemServicoService service) {
        this.service = service;
    }

    // CADASTRAR - POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServico cadastrar(
            @Valid @RequestBody OrdemServicoRequest dados) {

        return service.cadastrar(dados);
    }

    // LISTAR - GET
    @GetMapping
    public List<OrdemServico> listar() {
        return service.listar();
    }

    // BUSCAR POR ID - GET
    @GetMapping("/{id}")
    public OrdemServico buscarPorId(
            @PathVariable Integer id) {

        return service.buscarPorId(id);
    }

    // ATUALIZAR - PUT
    @PutMapping("/{id}")
    public OrdemServico atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody OrdemServicoRequest dados) {

        return service.atualizar(id, dados);
    }

    // EXCLUIR - DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Integer id) {

        service.excluir(id);
    }

    // TRATAR ERROS DE VALIDACAO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErro(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(erro -> {
            erros.put(
                    erro.getField(),
                    erro.getDefaultMessage()
            );
        });

        return ResponseEntity.badRequest().body(erros);
    }
}
