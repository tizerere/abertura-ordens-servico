
package br.com.ordensservico.repository;

import br.com.ordensservico.model.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdemServicoRepository
        extends JpaRepository<OrdemServico, Integer> {
}
