package alunounifacs.com.br.scee.model;

import java.io.Serializable;

/**
 * Created by omesquita on 29/04/16.
 */
public class Tarifa implements Serializable {
    private int id;
    private double tarifaBase;
    private double tarifaFinal;
    private double consumoMaximo;
    private double quantidadeKWh;
    private double valorTotal;
    private Tributo tributo;
    private Tipo tipo;

    public Tarifa() {
    }

    public Tarifa(int id, double tarifaBase, double consumoMaximo) {
        this.id = id;
        this.tarifaBase = tarifaBase;
        this.consumoMaximo = consumoMaximo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public double getTarifaFinal() {
        return tarifaFinal;
    }

    public void setTarifaFinal(double tarifaFinal) {
        this.tarifaFinal = tarifaFinal;
    }

    public double getConsumoMaximo() {
        return consumoMaximo;
    }

    public void setConsumoMaximo(double consumoMaximo) {
        this.consumoMaximo = consumoMaximo;
    }

    public Tributo getTributo() {
        return tributo;
    }

    public void setTributo(Tributo tributo) {
        this.tributo = tributo;
    }

    public double getQuantidadeKWh() {
        return quantidadeKWh;
    }

    public void setQuantidadeKWh(double quantidadeKWh) {
        this.quantidadeKWh = quantidadeKWh;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public double getValorTotal() {
        calculaTarifa();
        return valorTotal;
    }

    private void calculaTarifa() {
        tarifaFinal = tarifaBase / (tributo.getTotal() / 100);
        valorTotal = tarifaFinal * quantidadeKWh;
    }

}
