
package br.com.ordensservico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrdemServicoRequest {

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Equipamento é obrigatório")
    private Integer equipamentoId;

    public OrdemServicoRequest() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getEquipamentoId() {
        return equipamentoId;
    }

    public void setEquipamentoId(Integer equipamentoId) {
        this.equipamentoId = equipamentoId;
    }
}
