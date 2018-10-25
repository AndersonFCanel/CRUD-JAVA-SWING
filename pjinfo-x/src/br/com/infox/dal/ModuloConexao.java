package br.com.infox.dal;

import java.sql.*;

/**
 *
 * @author AHP
 */
public class ModuloConexao {
    //Método responsável por estabelecer a conexão com o banco.

    public static Connection conector() {
        java.sql.Connection conexao = null;
        // a linha abaixo chama o driver connector J
        String driver = "com.mysql.jdbc.Driver";
        //Armazenando informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbinfox";
        String user = "root";
        String password = "root";
        //estabelecendo a conexão com o banco
        try {
            Class.forName(driver);//Executa arquivo do driver
            conexao = DriverManager.getConnection(url, user, password);//ligando bd ao java
            System.out.println("Conectado ao BD!");
            return conexao;
        } catch (Exception e) {
            System.out.println("ERRO: " + e);
            return null;
        }
    }
}
