package br.com.ordensservico.service;
import br.com.ordensservico.model.Setor;
import br.com.ordensservico.repository.SetorRepository;
import br.com.ordensservico.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
@Service
public class SetorService {
 private final SetorRepository repository;
 private final EquipamentoRepository equipamentos;
 public SetorService(SetorRepository repository,EquipamentoRepository equipamentos){this.repository=repository;this.equipamentos=equipamentos;}
 public Setor cadastrar(Setor setor){setor.setId(null);return repository.save(setor);}
 public List<Setor> listar(){return repository.findAll();}
 public Setor buscarPorId(Integer id){return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Setor não encontrado"));}
 public Setor atualizar(Integer id,Setor dados){Setor atual=buscarPorId(id);atual.setNome(dados.getNome());return repository.save(atual);}
 public void excluir(Integer id){buscarPorId(id);if(equipamentos.existsBySetorId(id))throw new ResponseStatusException(HttpStatus.CONFLICT,"Setor possui equipamentos associados");repository.deleteById(id);}
}
