package alunounifacs.com.br.scee.model;

import java.io.Serializable;

/**
 * Created by omesquita on 29/04/16.
 */
public class Tributo implements Serializable {
    private int id;
    private double icms;
    private double pis;
    private double cofins;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIcms() {
        return icms;
    }

    public void setIcms(double icms) {
        this.icms = icms;
    }

    public double getPis() {
        return pis;
    }

    public void setPis(double pis) {
        this.pis = pis;
    }

    public double getCofins() {
        return cofins;
    }

    public void setCofins(double cofins) {
        this.cofins = cofins;
    }

    public double getTotal() {
        return this.icms + this.pis + this.cofins;
    }
}
