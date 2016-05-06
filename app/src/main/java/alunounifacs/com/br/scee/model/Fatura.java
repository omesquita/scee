package alunounifacs.com.br.scee.model;

import android.content.Context;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import alunounifacs.com.br.scee.dao.TipoDAO;

/**
 * Created by omesquita on 29/04/16.
 */
public class Fatura implements Serializable {
    private int id;
    private Date data;
    private double consumoKWh;
    private double valorFinal;
    private Tipo tipoDeTarifa;
    private List<Tarifa> tarifasAplicadas;
    private List<Departamento> departamentos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getConsumoKWh() {
        return consumoKWh;
    }

    public void setConsumoKWh(double consumoKWh) {
        this.consumoKWh = consumoKWh;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<Tarifa> getTarifasAplicadas() {
        return tarifasAplicadas;
    }

    public Tipo getTipoDeTarifa() {
        return tipoDeTarifa;
    }

    public void calculaFatura(Context context) {
        calcularConsumoTotal();

        double consumoTemp = 0;
        double consumoUsado = 0;

        for (Tarifa t : getTipoTarifa(context).getTarifas()) {
            if (consumoKWh >= t.getConsumoMaximo()) {
                t.setQuantidadeKWh(t.getConsumoMaximo() - consumoTemp);
                consumoUsado += t.getConsumoMaximo();
                consumoTemp = t.getConsumoMaximo() - consumoTemp;
            } else {
                t.setQuantidadeKWh(consumoKWh - consumoUsado);
            }
            valorFinal += t.getValorTotal();
            tarifasAplicadas.add(t);
        }
    }

    private void calcularConsumoTotal() {
        for (Departamento departamento : departamentos) {
            consumoKWh += departamento.calcularConsumo();
        }
    }

    private Tipo getTipoTarifa(Context context) {
        List<Tipo> tipos = new TipoDAO(context).getAll();

        for (Tipo tipo : tipos) {
            if (consumoKWh > tipo.getConsumoMaximo())
                continue;
            return tipo;
        }
        return null;
    }
}
