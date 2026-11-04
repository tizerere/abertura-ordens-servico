
package br.com.ordensservico.service;

import br.com.ordensservico.dto.EquipamentoRequest;
import br.com.ordensservico.model.Equipamento;
import br.com.ordensservico.model.Setor;
import br.com.ordensservico.repository.EquipamentoRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class EquipamentoService {

    private final EquipamentoRepository repository;
    private final SetorService setores;

    public EquipamentoService(
            EquipamentoRepository repository,
            SetorService setores) {
        this.repository = repository;
        this.setores = setores;
    }

    public List<Equipamento> listar() {
        return repository.findAll();
    }

    public Equipamento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Equipamento não encontrado"));
    }

    private Setor resolverSetor(Integer setorId) {

        if (setorId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Setor é obrigatório");
        }

        try {
            return setores.buscarPorId(setorId);

        } catch (ResponseStatusException ex) {

            if (ex.getStatusCode().value() == 404) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Setor não encontrado");
            }

            throw ex;
        }
    }

    public Equipamento cadastrar(EquipamentoRequest dados) {

        Setor setor = resolverSetor(dados.getSetorId());

        if (repository.existsByNumeroPatrimonio(
                dados.getNumeroPatrimonio())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Patrimônio já cadastrado");
        }

        Equipamento equipamento = new Equipamento();

        equipamento.setNome(dados.getNome());
        equipamento.setNumeroPatrimonio(
                dados.getNumeroPatrimonio());
        equipamento.setSetor(setor);

        return repository.save(equipamento);
    }

    public Equipamento atualizar(
            Integer id,
            EquipamentoRequest dados) {

        Equipamento atual = buscarPorId(id);

        Setor setor = resolverSetor(dados.getSetorId());

        if (repository.existsByNumeroPatrimonioAndIdNot(
                dados.getNumeroPatrimonio(), id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Patrimônio já cadastrado");
        }

        atual.setNome(dados.getNome());
        atual.setNumeroPatrimonio(
                dados.getNumeroPatrimonio());
        atual.setSetor(setor);

        return repository.save(atual);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}
