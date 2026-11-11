
package br.com.ordensservico.repository;

import br.com.ordensservico.model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemServicoRepository
        extends JpaRepository<OrdemServico, Integer> {

    // Busca somente ordens que ainda estao abertas
    List<OrdemServico> findByDataEncerramentoIsNull();
}
