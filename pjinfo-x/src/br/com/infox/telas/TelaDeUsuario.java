/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import br.com.infox.dal.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author AHP
 */
public class TelaDeUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;//variável criada no modulo de conexao
    PreparedStatement pst = null; //Framework para instrução sql
    ResultSet rs = null;

    public TelaDeUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void consultar() {
        String sql = "select * from tbusuarios where iduser=?";
        try {
            //Fazendo a consulta no banco e armazenando em pst
            pst = conexao.prepareStatement(sql);
            //'setando' valor do campo 'id' no lugar do '?' na query sql.
            pst.setString(1, txtUserId.getText());
            //executando a query
            rs = pst.executeQuery();

            if (rs.next()) {
                //Pegando o segundo campo da tabela consultada e escrevendo no label txtUserNome
                txtUserNome.setText(rs.getString(2));
                txtUserFone.setText(rs.getString(3));
                txtUserLogin.setText(rs.getString(4));
                txtUserSenha.setText(rs.getString(5));
                // a linha abaixo 'seta' o comboBox
                cboUserPerfil.setSelectedItem(rs.getString(6));
            } else {
                //Exibindo um alerta e limpando os campos de texto
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
                txtUserId.setText(null);
                txtUserNome.setText(null);
                txtUserFone.setText(null);
                txtUserLogin.setText(null);
                txtUserSenha.setText(null);
                // cboUserPerfil.setSelectedItem(null);// removido para evitar bug na validação
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //método para adicionar usuários na tabela tbusuarios no banco de dados
    private void adicionar() {
        String sql = "insert into tbusuarios(iduser, usario, fone, login, senha, perfil)"
                + "values(?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            //coletando valores digitados nos campos e texto
            pst.setString(1, txtUserId.getText());
            pst.setString(2, txtUserNome.getText());
            pst.setString(3, txtUserFone.getText());
            pst.setString(4, txtUserLogin.getText());
            pst.setString(5, txtUserSenha.getText());
            pst.setString(6, cboUserPerfil.getSelectedItem().toString());

            //validação dos campos definidos como obrigatórios
            if (txtUserId.getText().isEmpty()
                    || txtUserNome.getText().isEmpty()
                    || txtUserLogin.getText().isEmpty()
                    || txtUserSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //a linha abaixo atualiza a tbusuarios com os dados coletados do formulario
                //a estrutura abaixo é utilizada para confirmar a inserção de dados  na tbusuarios
                int adicionado = pst.executeUpdate();
                //Caso tudo tenha ocorriodo conforme o esperado, o metodo abaixo irá limpar os campos apos a executeUpdate()
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!");
                    pst = conexao.prepareStatement(sql);
                    txtUserId.setText(null);
                    txtUserNome.setText(null);
                    txtUserFone.setText(null);
                    txtUserLogin.setText(null);
                    txtUserSenha.setText(null);
                    //cboUserPerfil.setAction(null);// removido para evitar bug na validação
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //O método abaixo serve para atualizar a tbusuarios
    private void alterar() {
        String sql = "update tbusuarios set usario=?, fone=?, login=?, senha=?, perfil=? "
                + "where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtUserNome.getText());
            pst.setString(2, txtUserFone.getText());
            pst.setString(3, txtUserLogin.getText());
            pst.setString(4, txtUserSenha.getText());
            pst.setString(5, cboUserPerfil.getSelectedItem().toString());
            pst.setString(6, txtUserId.getText());

            //validação dos campos definidos como obrigatórios
            if (txtUserId.getText().isEmpty()
                    || txtUserNome.getText().isEmpty()
                    || txtUserLogin.getText().isEmpty()
                    || txtUserSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //a linha abaixo atualiza a tbusuarios com os dados coletados do formulario
                //a estrutura abaixo é utilizada para confirmar a alteração de dados  na tbusuarios
                int adicionado = pst.executeUpdate();
                //Caso tudo tenha ocorriodo conforme o esperado, o metodo abaixo irá limpar os campos apos a executeUpdate()
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Usuário alterados com sucesso!");
                    pst = conexao.prepareStatement(sql);
                    txtUserId.setText(null);
                    txtUserNome.setText(null);
                    txtUserFone.setText(null);
                    txtUserLogin.setText(null);
                    txtUserSenha.setText(null);
                    //cboUserPerfil.setAction(null);// removido para evitar bug na validação
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }

    //O método abaixo é responsável pela remoção dos usuários da tbUsuarios
    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Pense bem!\n Você tem certeza dessa remoção?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "\tdelete from tbusuarios where iduser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUserId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
                    txtUserId.setText(null);
                    txtUserNome.setText(null);
                    txtUserFone.setText(null);
                    txtUserLogin.setText(null);
                    txtUserSenha.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUserId = new javax.swing.JTextField();
        txtUserLogin = new javax.swing.JTextField();
        cboUserPerfil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtUserFone = new javax.swing.JTextField();
        btnUserCreate = new javax.swing.JButton();
        btnUserDelete = new javax.swing.JButton();
        btnUserUpdate = new javax.swing.JButton();
        btnUserRead = new javax.swing.JButton();
        txtUserNome = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtUserSenha = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Usuarios");
        setPreferredSize(new java.awt.Dimension(580, 478));

        jLabel1.setText("*Id:");

        jLabel2.setText("*Nome: ");

        jLabel3.setText("*Login: ");

        jLabel4.setText("*Senha:");

        jLabel5.setText("*Perfil:");

        txtUserId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserIdActionPerformed(evt);
            }
        });

        txtUserLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserLoginActionPerformed(evt);
            }
        });

        cboUserPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin\t", "user" }));

        jLabel6.setText("Telefone");

        txtUserFone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserFoneActionPerformed(evt);
            }
        });

        btnUserCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnUserCreate.setToolTipText("Adicionar");
        btnUserCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUserCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserCreateActionPerformed(evt);
            }
        });

        btnUserDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnUserDelete.setToolTipText("Deletar");
        btnUserDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUserDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserDeleteActionPerformed(evt);
            }
        });

        btnUserUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnUserUpdate.setToolTipText("Alterar");
        btnUserUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserUpdate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUserUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserUpdateActionPerformed(evt);
            }
        });

        btnUserRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnUserRead.setToolTipText("Consultar");
        btnUserRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserRead.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUserRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserReadActionPerformed(evt);
            }
        });

        txtUserNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserNomeActionPerformed(evt);
            }
        });

        jLabel7.setText("*Campos obrigatórios");

        txtUserSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(6, 6, 6))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtUserId, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtUserNome)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUserFone, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(txtUserSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cboUserPerfil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(66, 66, 66))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(btnUserCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUserRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnUserDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUserId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUserNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(txtUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboUserPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(txtUserSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 23, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUserRead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(161, 161, 161))
        );

        setBounds(0, 0, 580, 478);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserIdActionPerformed

    private void txtUserLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserLoginActionPerformed

    private void txtUserFoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserFoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserFoneActionPerformed

    private void btnUserCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserCreateActionPerformed
        adicionar();
    }//GEN-LAST:event_btnUserCreateActionPerformed

    private void btnUserDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserDeleteActionPerformed
        remover();
    }//GEN-LAST:event_btnUserDeleteActionPerformed

    private void btnUserUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserUpdateActionPerformed
        alterar();
    }//GEN-LAST:event_btnUserUpdateActionPerformed

    private void btnUserReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserReadActionPerformed
        consultar();
    }//GEN-LAST:event_btnUserReadActionPerformed

    private void txtUserNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserNomeActionPerformed

    private void txtUserSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserSenhaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUserCreate;
    private javax.swing.JButton btnUserDelete;
    private javax.swing.JButton btnUserRead;
    private javax.swing.JButton btnUserUpdate;
    private javax.swing.JComboBox<String> cboUserPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtUserFone;
    private javax.swing.JTextField txtUserId;
    private javax.swing.JTextField txtUserLogin;
    private javax.swing.JTextField txtUserNome;
    private javax.swing.JTextField txtUserSenha;
    // End of variables declaration//GEN-END:variables
}
