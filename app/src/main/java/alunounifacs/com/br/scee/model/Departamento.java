package alunounifacs.com.br.scee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by omesquita on 27/04/16.
 */
public class Departamento implements Serializable {

    private int id;
    private String descricao;
    private double consumo;
    private double valor;
    private List<Equipamento> equipamentos = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double calcularConsumo() {
        consumo = 0;
        for (Equipamento equipamento : equipamentos) {
            consumo += equipamento.calcularConsumo();
        }

        return consumo;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public void calcularValorFinal(double valorKWh) {
        this.valor = 0;
        for(Equipamento equipamento : equipamentos) {
            valor += equipamento.calcularValorFinal(valorKWh);
        }
    }
}
