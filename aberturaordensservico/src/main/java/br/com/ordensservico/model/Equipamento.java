package br.com.ordensservico.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name="equipamento")
public class Equipamento {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Integer id;
 @NotBlank(message="Nome é obrigatório") @Column(nullable=false) private String nome;
 @NotBlank(message="Número de patrimônio é obrigatório") @Column(nullable=false,unique=true) private String numeroPatrimonio;
 @NotNull(message="Setor é obrigatório") @ManyToOne(optional=false) @JoinColumn(name="setor_id",nullable=false) private Setor setor;
 public Integer getId(){return id;}
 public void setId(Integer id){this.id=id;}
 public String getNome(){return nome;}
 public void setNome(String nome){this.nome=nome;}
 public String getNumeroPatrimonio(){return numeroPatrimonio;}
 public void setNumeroPatrimonio(String numeroPatrimonio){this.numeroPatrimonio=numeroPatrimonio;}
 public Setor getSetor(){return setor;}
 public void setSetor(Setor setor){this.setor=setor;}
}
