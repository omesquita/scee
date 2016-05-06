package alunounifacs.com.br.scee.dao.utils;

/**
 * Created by omesquita on 01/05/16.
 */
public abstract class ScriptSQLite {
    public static String departamentoV1() {
        return "CREATE TABLE IF NOT EXISTS departamento (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "descricao TEXT NOT NULL," +
                "consumo DOUBLE," +
                "valorConsumo DOUBLE);";
    }

    public static String equipamentoV1() {
        return "CREATE TABLE IF NOT EXISTS equipamento (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "descricao TEXT NOT NULL, potencia DOUBLE NOT NULL, " +
                "horas_dias INTEGER, " +
                "dias_mes INTEGER, " +
                "consumo DOUBLE, " +
                "valorConsumo DOUBLE, " +
                "id_departamento INTEGER NOT NULL, " +
                "FOREIGN KEY(id_departamento) REFERENCES departamento (id));";
    }

    public static String tributoV1() {
        return "CREATE TABLE IF NOT EXISTS tributo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "icms DOUBLE, " +
                "pis DOUBLE, " +
                "cofins DOUBLE);";
    }

    public static String tipoV1() {
        return "CREATE TABLE IF NOT EXISTS tipo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "descricao TEXT NOT NULL, " +
                "consumo_maximo DOUBLE NOT NULL);";
    }

    public static String tarifaV1() {
        return "CREATE TABLE IF NOT EXISTS tarifa (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "tarifa_base DOUBLE NOT NULL, " +
                "tarifa_final DOUBLE, " +
                "consumo_maximo DOUBLE NOT NULL, " +
                "quantidade_kwh DOUBLE, " +
                "valor_total DOUBLE, " +
                "id_tipo INTEGER NOT NULL, " +
                "id_tributo INTEGER NOT NULL, " +
                "FOREIGN KEY(id_tipo) REFERENCES tipo (id), " +
                "FOREIGN KEY(id_tributo) REFERENCES tributo (id));";
    }

    public static String faturaV1() {
        return "CREATE TABLE IF NOT EXISTS fatura (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "valor_total DOUBLE, " +
                "data_fechamento DATE" +
                "consumo_kwh DOUBLE);";
    }

    public static String faturahasTarifaV1() {
        return "CREATE TABLE IF NOT EXISTS fatura_has_tarifa (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "id_fatura INTEGER NOT NULL, " +
                "id_tarifa INTEGER NOT NULL, " +
                "FOREIGN KEY(id_fatura) REFERENCES fatura (id), " +
                "FOREIGN KEY(id_tarifa) REFERENCES tarifa (id));";
    }

    public static String departamentoHasFaturaV1() {
        return "CREATE TABLE IF NOT EXISTS departamento_has_fatura (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "id_departamento INTEGER NOT NULL, " +
                "id_fatura INTEGER NOT NULL, " +
                "FOREIGN KEY(id_departamento) REFERENCES departamento (id), " +
                "FOREIGN KEY(id_fatura) REFERENCES fatura (id));";
    }

    public static String[] populateTipo() {
        return new String[] {
                "INSERT INTO tipo (descricao, consumo_maximo) VALUES ('BAIXA RENDA (até 50,00 kWh)', 50.0);",
                "INSERT INTO tipo (descricao, consumo_maximo) VALUES ('BAIXA RENDA (até 149,99 kWh)', 149.99);",
                "INSERT INTO tipo (descricao, consumo_maximo) VALUES ('BAIXA RENDA (igual ou superior a 150 kWh)', 999999999);"
        };
    }

    public static String[] populateTributo() {
        return new String[]{
                "INSERT INTO tributo (icms, pis, cofins) VALUES (0, 0.39, 1.8);",
                "INSERT INTO tributo (icms, pis, cofins) VALUES (25, 0.39, 1.8);",
                "INSERT INTO tributo (icms, pis, cofins) VALUES (27, 0.39, 1.8);"
        };
    }

    public static String[] populateTarifa() {
        return new String[]{
                "INSERT INTO tarifa (tarifa_base, tarifa_final, consumo_maximo, quantidade_kwh, valor_total, id_tipo, id_tributo) VALUES (0.134096, NULL, 30, '', NULL, 1, 1);",
                "INSERT INTO tarifa (tarifa_base, tarifa_final, consumo_maximo, quantidade_kwh, valor_total, id_tipo, id_tributo) VALUES (0.229878, NULL, 50, '', NULL, 1, 1);",
                "INSERT INTO tarifa (tarifa_base, tarifa_final, consumo_maximo, quantidade_kwh, valor_total, id_tipo, id_tributo) VALUES (0.134096, '', 30, '', NULL, 2, 2);",
                "INSERT INTO tarifa (tarifa_base, tarifa_final, consumo_maximo, quantidade_kwh, valor_total, id_tipo, id_tributo) VALUES (0.229878, '', 100, '', '', 2, 2);",
                "INSERT INTO tarifa (tarifa_base, tarifa_final, consumo_maximo, quantidade_kwh, valor_total, id_tipo, id_tributo) VALUES (0.344817, '', 149.99, '', NULL, 2, 2);",
                "INSERT INTO tarifa (tarifa_base, tarifa_final, consumo_maximo, quantidade_kwh, valor_total, id_tipo, id_tributo) VALUES (0.134096, '', 30, '', '', 3, 3);",
                "INSERT INTO tarifa (tarifa_base, tarifa_final, consumo_maximo, quantidade_kwh, valor_total, id_tipo, id_tributo) VALUES (0.229878, '', 100, '', '', 3, 3);",
                "INSERT INTO tarifa (tarifa_base, tarifa_final, consumo_maximo, quantidade_kwh, valor_total, id_tipo, id_tributo) VALUES (0.344817, '', 220, '', '', 3, 3);",
                "INSERT INTO tarifa (tarifa_base, tarifa_final, consumo_maximo, quantidade_kwh, valor_total, id_tipo, id_tributo) VALUES (0.38313, '', 1e+09, '', '', 3, 3);"
        };
    }
}
