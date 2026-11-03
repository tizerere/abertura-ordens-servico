
package br.com.ordensservico.controller;

import br.com.ordensservico.model.Equipamento;
import br.com.ordensservico.service.EquipamentoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService service;

    public EquipamentoController(EquipamentoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipamento cadastrar(@Valid @RequestBody Equipamento dados) {
        return service.cadastrar(dados);
    }

    @GetMapping
    public List<Equipamento> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Equipamento buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Equipamento atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Equipamento dados) {
        return service.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }

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
