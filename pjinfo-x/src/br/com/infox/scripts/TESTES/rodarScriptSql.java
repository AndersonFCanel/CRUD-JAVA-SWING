/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.scripts.TESTES;

import br.com.infox.dal.ModuloConexao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author AHP
 */
public class rodarScriptSql {

  public static  String createDB = ("create database if not exists dbinfox;\n"
            + "use dbinfox;\n"
            + "create table if not exists tbusuarios(\n"
            + "	iduser int primary key,\n"
            + "	usario varchar(50) not null,\n"
            + "	fone varchar(15),\n"
            + "	login varchar(15) not null unique, \n"
            + "	senha varchar(15) not null\n"
            + ");"
            + ""
            + "    create table IF NOT EXISTS \n"
            + "        tbclientes(idcli int primary key auto_increment,\n"
            + "                nomecli varchar(50) not null,\n"
            + "	endcli varchar(100),\n"
            + "	fonecli varchar(50) not null,\n"
            + "	emailcli varchar(50)\n"
            + ");"
            + ""
            + "create table IF NOT EXISTS \n"
            + "        tbos(os int primary key auto_increment,\n"
            + "            data_os timestamp default current_timestamp ,\n"
            + "            equipamento varchar(50) not null,\n"
            + "	defeito varchar(150) not null,\n"
            + "	servico varchar(150),\n"
            + "	tecnico varchar(30),\n"
            + "	valor decimal(10, 2), -- dez digitos com duas casas decimais depois da virgula\n"
            + "	idcli int not null,\n"
            + "    foreign key(idcli) references tbclientes(idcli)\n"
            + ");	"
            + " alter table tbusuarios add column perfil \n"
            + "            varchar(20) not null;\n"
            + ""
            + "");


    public static String insertDB = ("insert into tbusuarios(iduser, usario, fone, login, senha )\n"
            + "values(1,'José de Assis' , '99999-9999','joseassis', '12345');"
            + "insert into tbusuarios(iduser, usario, fone, login, senha )\n"
            + "values(2,'Administrador' , '99999-9999','admin', 'admin');\n"
            + "insert into tbusuarios(iduser, usario, fone, login, senha )\n"
            + "values(3,'Bill Gates' , '99999-9999','Bill', '12345');\n"
            + "insert into tbusuarios(iduser, usario, fone, login, senha )\n"
            + "values(4,'Leandro Ramos' , '99999-9999','leandro', '123');"
            + "insert into tbusuarios(iduser, usario, fone, login, senha )\n"
            + "values(2,'Administrador' , '99999-9999','admin', 'admin');"
            + "insert into tbusuarios(iduser, usario, fone, login, senha )\n"
            + "    values(3,'Bill Gates' , '99999-9999','Bill', '12345');\n"
            + "insert into tbusuarios(iduser, usario, fone, login, senha )\n"
            + "    values(4,'Leandro Ramos' , '99999-9999','leandro', '123');"
            + ""
            + "update tbusuarios set fone='8888-8888' where iduser=2;"
            + ""
            + "  insert into tbclientes(nomeCli, endcli, fonecli, emailcli)\n"
            + "    values('Linus Torvalds', 'Rua Tux, 2015', '9999-9999',  'linus@lunux.com');"
            + ""
            + " insert into tbos(equipamento, defeito, servico, tecnico, valor, idcli)\n"
            + "    values ('NOTEBOOK','Não Liga','Troca da fonte','Zé',87.50,1 );  "
            + ""
            + "\n"
            + "    update tbusuarios set perfil = 'admin' where iduser = 1;\n"
            + "    update tbusuarios set perfil = 'admin' where iduser = 2;\n"
            + "    update tbusuarios set perfil = 'user' where iduser = 3;");

    
// public class SQLite {
    private static Connection connection;
    private Statement statement;
    private ResultSet resultset;
    public rodarScriptSql() {
        openDatabase();
    }
    public static Connection getConnection() {
        return connection;
    }
    public ResultSet getResultset() {
        return resultset;
    }
    public Statement getStatement() {
        return statement;
    }
    public static Statement createStatement() {
        try {
            Statement stmt = connection.createStatement();
            return stmt;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    private void openDatabase() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dbinfox";//criar o arquivo na pasta de projeto chamado : meusdados.db
        String user = "root";
        String pass = "";
        System.out.println("Conectando ao Banco de Dados...");
        //JOptionPane.showMessageDialog(null, "Conectando ao Banco de Dados");
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexão OK");
            statement = connection.createStatement();
            checkEstruturadeTabelas();
        } catch (ClassNotFoundException e) {
            System.out.println("Falha de conexão faltando drivers.");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Falha de conexão ou na checagem das estruturas das Tabelas!");
            JOptionPane.showMessageDialog(null, e);
            System.exit(1);
        }
    }
    private void checkEstruturadeTabelas() {
        try {
            this.resultset = this.statement.executeQuery("select * from tbos;");// checar se tem tabelas e criar tableas automaticamente ou importar de um script.sql talves...
            this.resultset = this.statement.executeQuery("select * from tbclientes;");
            this.resultset = this.statement.executeQuery("select * from tbusuarios;");
            System.out.println("Estruturas das Tabelas OK");
        } catch (SQLException e) {
            System.out.println("Banco de Dados sem estruturas de tabelas...");
            System.out.println("Iniciando Assitente para configuração do Banco de Dados...");
            openAssitenteDialog();//chamar por exemplo interação com usuario para executar script sql manualmente !
            System.out.println("Aguardando interação com usuário...");
            //e.printStackTrace();
        }
    }
    //JDInstall dialog;
    private void openAssitenteDialog() {
        //dialog = new JDInstall(null, true);
        //dialog.setVisible(true);
    }
    /*private void openAssitenteDialogThread() {
     java.awt.EventQueue.invokeLater(new Runnable() {
     public void run() {
     dialog = new JDInstall(null, true);
     dialog.addWindowListener(new java.awt.event.WindowAdapter() {
     @Override
     public void windowClosing(java.awt.event.WindowEvent e) {
     //System.exit(0);
     }
     });
     dialog.setVisible(true);
     }
     });
     }*/
    public static boolean executeScript(String script) {
        System.out.println(script);
        try {
            createStatement().executeUpdate(script);
            //this.connection.commit();
            System.out.println("Criação de Estruturas das Tabelas OK");
            return true;
        } catch (SQLException e) {
            /*try {
             //this.connection.rollback();
             System.out.println("Erro ao executar script...");
             System.out.println("Erro: " + e);
             } catch (SQLException ex) {
             System.out.println("Erro em rollback: " + ex);
             }*/
            JOptionPane.showMessageDialog(null, "ERRO:" + e.getErrorCode() + "\n" + e, "ERRO DE EXECUÇÃO", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
    public static void main(String[] args) {
        executeScript(createDB);
    }

    
    
    
    
}
