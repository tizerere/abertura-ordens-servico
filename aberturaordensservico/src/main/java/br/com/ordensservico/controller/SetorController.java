
package br.com.ordensservico.controller;

import br.com.ordensservico.model.Setor;
import br.com.ordensservico.service.SetorService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService service;

    public SetorController(SetorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Setor cadastrar(@Valid @RequestBody Setor dados) {
        return service.cadastrar(dados);
    }

    @GetMapping
    public List<Setor> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Setor buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Setor atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Setor dados) {
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

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        return ResponseEntity.badRequest()
                .body(Map.of("mensagem", mensagem));
    }
}
