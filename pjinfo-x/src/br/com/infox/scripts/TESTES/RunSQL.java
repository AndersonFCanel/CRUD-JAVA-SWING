package br.com.infox.scripts.TESTES;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RunSQL {

    public static void main(String[] args) {
        String createDB = "create database if not exists TESTE1; \n"
                + "create table if not exists tbusuarios(\n"
                + "	iduser int primary key,\n"
                + "	usario varchar(50) not null,\n"
                + "	fone varchar(15),\n"
                + "	login varchar(15) not null unique, \n"
                + "	senha varchar(15) not null\n"
                + ");"
                + "create table IF NOT EXISTS tbclientes(\n"
                + "    idcli int primary key auto_increment,\n"
                + "    nomecli varchar(50) not null,\n"
                + "    endcli varchar(100),\n"
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
                + "            varchar(20) not null;";

        System.out.println(createDB);

//        String insertDB = "insert into tbusuarios(iduser, usario, fone, login, senha, perfil )\n"
//                + "values(1,'José de Assis' , '99999-9999','joseassis', '12345','admin');\n"
//                + ""
//                + "insert into tbusuarios(iduser, usario, fone, login, senha , perfil)\n"
//                + "values(2,'Administrador' , '99999-9999','admin', 'admin'); 'user'\n"
//                + ""
//                + "insert into tbusuarios(iduser, usario, fone, login, senha, perfil )\n"
//                + "values(3,'Bill Gates' , '99999-9999','Bill', '12345', 'user');\n"
//                + ""
//                + "insert into tbusuarios(iduser, usario, fone, login, senha, perfil )\n"
//                + "values(4,'Leandro Ramos' , '99999-9999','leandro', '123', 'user');\n"
//                + ""
//                + "insert into tbusuarios(iduser, usario, fone, login, senha, perfil )\n"
//                + "values(2,'Administrador' , '99999-9999','admin', 'admin', 'admin');\n"
//                + ""
//                + "insert into tbusuarios(iduser, usario, fone, login, senha, perfil )\n"
//                + "    values(3,'Bill Gates' , '99999-9999','Bill', '12345', 'user');\n"
//                + ""
//                + "insert into tbusuarios(iduser, usario, fone, login, senha, perfil )\n"
//                + "    values(4,'Leandro Ramos' , '99999-9999','leandro', '123', 'user');\n"
//                + ""
//                + "update tbusuarios set fone='8888-8888' where iduser=2;\n"
//                + ""
//                + "  insert into tbclientes(nomeCli, endcli, fonecli, emailcli)\n"
//                + "    values('Linus Torvalds', 'Rua Tux, 2015', '9999-9999',  'linus@lunux.com');\n"
//                + ""
//                + " insert into tbos(equipamento, defeito, servico, tecnico, valor, idcli)\n"
//                + "    values ('NOTEBOOK','Não Liga','Troca da fonte','Zé',87.50,1 ); \n"
//                + ""
//                + "    update tbusuarios set perfil = 'admin' where iduser = 1;\n"
//                + "    update tbusuarios set perfil = 'admin' where iduser = 2;\n"
//                + "    update tbusuarios set perfil = 'user' where iduser = 3;";
//
//        System.out.println(insertDB);

        // muda aqui para o seu server.
        String host = "localhost:3306";
        String db = "TESTE1";
        String username = "root";
        String password = "";
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "?user=" + username
                    + "&password=" + password);
            st = con.createStatement();
            rs = st.executeQuery("show databases");
            while (rs.next()) {
                int i = 1;
                System.out.println(rs.getString(i));
                i++;
            }

            String[] createDBaux = createDB.split(";");
//            String[] insertDBaux = insertDB.split(";");
            //System.out.println(Arrays.toString(createDBaux));

            for (int i = 0; i < createDBaux.length; i++) {
                st.executeUpdate(createDBaux[i] + ";  ");
               // st.executeUpdate(insertDBaux[i] + ";  ");
            }

        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
