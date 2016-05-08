package alunounifacs.com.br.scee.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by omesquita on 29/04/16.
 */
public class Tipo implements Serializable {

    private int id;
    private String descricao;
    private double consumoMaximo;
    private List<Tarifa> tarifas;

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

    public double getConsumoMaximo() {
        return consumoMaximo;
    }

    public void setConsumoMaximo(double consumoMaximo) {
        this.consumoMaximo = consumoMaximo;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<Tarifa> tarifas) {
        this.tarifas = tarifas;
    }
}
