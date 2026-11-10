
package br.com.ordensservico.service;

import br.com.ordensservico.dto.OrdemServicoRequest;
import br.com.ordensservico.model.OrdemServico;
import br.com.ordensservico.model.Equipamento;
import br.com.ordensservico.repository.OrdemServicoRepository;
import br.com.ordensservico.repository.EquipamentoRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository repository;
    private final EquipamentoRepository equipamentoRepository;

    public OrdemServicoService(
            OrdemServicoRepository repository,
            EquipamentoRepository equipamentoRepository) {

        this.repository = repository;
        this.equipamentoRepository = equipamentoRepository;
    }

    // CADASTRAR
    public OrdemServico cadastrar(OrdemServicoRequest dados) {

        Equipamento equipamento = buscarEquipamento(
                dados.getEquipamentoId());

        OrdemServico ordem = new OrdemServico();

        ordem.setDescricao(dados.getDescricao());
        ordem.setEquipamento(equipamento);
        ordem.setDataAbertura(LocalDateTime.now());

        return repository.save(ordem);
    }

    // LISTAR
    public List<OrdemServico> listar() {
        return repository.findAll();
    }

    // BUSCAR POR ID
    public OrdemServico buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ordem de serviço não encontrada"));
    }

    // ATUALIZAR
    public OrdemServico atualizar(
            Integer id, OrdemServicoRequest dados) {

        OrdemServico ordem = buscarPorId(id);

        Equipamento equipamento = buscarEquipamento(
                dados.getEquipamentoId());

        ordem.setDescricao(dados.getDescricao());
        ordem.setEquipamento(equipamento);

        // Mantém a data original de abertura
        return repository.save(ordem);
    }

    // EXCLUIR
    public void excluir(Integer id) {

        OrdemServico ordem = buscarPorId(id);

        repository.delete(ordem);
    }

    // VERIFICAR EQUIPAMENTO
    private Equipamento buscarEquipamento(Integer id) {

        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Equipamento não encontrado"));
    }
}
