package br.com.ordensservico.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name="setor")
public class Setor {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Integer id;
 @NotBlank(message="Nome é obrigatório") @Column(nullable=false) private String nome;
 public Integer getId(){return id;}
 public void setId(Integer id){this.id=id;}
 public String getNome(){return nome;}
 public void setNome(String nome){this.nome=nome;}
}
