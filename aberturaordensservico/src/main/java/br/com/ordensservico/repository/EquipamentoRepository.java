package br.com.ordensservico.repository;
import br.com.ordensservico.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EquipamentoRepository extends JpaRepository<Equipamento,Integer> {
 boolean existsBySetorId(Integer setorId);
 boolean existsByNumeroPatrimonio(String numeroPatrimonio);
 boolean existsByNumeroPatrimonioAndIdNot(String numeroPatrimonio,Integer id);
}
