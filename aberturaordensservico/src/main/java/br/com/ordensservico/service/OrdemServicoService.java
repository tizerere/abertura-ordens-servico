
package br.com.ordensservico.service;

import br.com.ordensservico.dto.OrdemServicoRequest;
import br.com.ordensservico.dto.EncerramentoOrdemServicoRequest;

import br.com.ordensservico.model.OrdemServico;
import br.com.ordensservico.model.Equipamento;

import br.com.ordensservico.repository.OrdemServicoRepository;
import br.com.ordensservico.repository.EquipamentoRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdemServicoService {

    private final OrdemServicoRepository repository;
    private final EquipamentoRepository equipamentoRepository;

    // CONSTRUTOR
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

    // LISTAR TODAS
    public List<OrdemServico> listar() {
        return repository.findAll();
    }

    // LISTAR SOMENTE ORDENS ABERTAS
    public List<OrdemServico> listarAbertas() {
        return repository.findByDataEncerramentoIsNull();
    }

    // BUSCAR POR ID
    public OrdemServico buscarPorId(Integer id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ordem de serviço não encontrada"
                ));
    }

    // ATUALIZAR
    public OrdemServico atualizar(
            Integer id,
            OrdemServicoRequest dados) {

        OrdemServico ordem = buscarPorId(id);

        Equipamento equipamento = buscarEquipamento(
                dados.getEquipamentoId());

        ordem.setDescricao(dados.getDescricao());
        ordem.setEquipamento(equipamento);

        return repository.save(ordem);
    }

    // EXCLUIR
    public void excluir(Integer id) {

        OrdemServico ordem = buscarPorId(id);

        repository.delete(ordem);
    }

    // ENCERRAR ORDEM DE SERVICO
    @Transactional
    public OrdemServico encerrar(
            Integer id,
            EncerramentoOrdemServicoRequest dados) {

        OrdemServico ordem = buscarPorId(id);

        // Impede encerramento duplicado
        if (ordem.getDataEncerramento() != null) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta ordem de serviço já foi encerrada"
            );
        }

        // Registra a solucao
        ordem.setSolucao(dados.getSolucao());

        // Registra data e hora automaticamente
        ordem.setDataEncerramento(LocalDateTime.now());

        return repository.save(ordem);
    }

    // BUSCAR EQUIPAMENTO
    private Equipamento buscarEquipamento(Integer id) {

        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Equipamento não encontrado"
                ));
    }
}
