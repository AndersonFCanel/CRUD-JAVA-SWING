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
import net.proteanit.sql.DbUtils;

/**
 *
 * @author AHP
 */
public class TelaDeCLiente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaDeCLiente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    //método para adicionar usuários na tabela tbclientes no banco de dados
    private void adicionar() {
        String sql = "insert into tbclientes( nomecli, endcli, fonecli, emailcli)"
                + "values(?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            //coletando valores digitados nos campos e texto
            pst.setString(1, txtClienteNome.getText());
            pst.setString(2, txtClienteEndereco.getText());
            pst.setString(3, txtClienteTelefone.getText());
            pst.setString(4, txtClienteEmail.getText());

            //validação dos campos definidos como obrigatórios
            if (txtClienteNome.getText().isEmpty()
                    || txtClienteTelefone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //a linha abaixo atualiza a tbusuarios com os dados coletados do formulario
                //a estrutura abaixo é utilizada para confirmar a inserção de dados  na tbusuarios
                int adicionado = pst.executeUpdate();
                //Caso tudo tenha ocorriodo conforme o esperado, o metodo abaixo irá limpar os campos apos a executeUpdate()
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
                    pst = conexao.prepareStatement(sql);
                    txtClienteNome.setText(null);
                    txtClienteEndereco.setText(null);
                    txtClienteTelefone.setText(null);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
//Método para pesquisar na tbclientes

    private void pesquisar() {
        //String sql = "select * from tbclientes where nomecli like ?";
         String sql = "select idcli as ID, nomecli as NOME, endcli as ENDEREÇO, fonecli as FONE, emailcli as EMAIL  from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //passando o conteúdo da caixa de pesquisa
            //atenção ao '%', é a continuação da string sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblClientesTabela.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //método para setar os campos da tabela nos campos de texo
    public void setar_campos() {
        int setar = tblClientesTabela.getSelectedRow();
        txtClienteId.setText(tblClientesTabela.getModel().getValueAt(setar, 0).toString());
        txtClienteNome.setText(tblClientesTabela.getModel().getValueAt(setar, 1).toString());
        txtClienteEndereco.setText(tblClientesTabela.getModel().getValueAt(setar, 2).toString());
        txtClienteTelefone.setText(tblClientesTabela.getModel().getValueAt(setar, 3).toString());
        txtClienteEmail.setText(tblClientesTabela.getModel().getValueAt(setar, 4).toString());

        // o método abaixo desabilita o botão adicionar
        btnUserCreate.setEnabled(false);
    }

    //O método abaixo serve para atualizar a tbclientes no BD
    private void alterar() {
        String sql = "update tbclientes set nomecli=?, endcli=?, fonecli=?, emailcli=?"
                + "where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtClienteNome.getText());
            pst.setString(2, txtClienteEndereco.getText());
            pst.setString(3, txtClienteTelefone.getText());
            pst.setString(4, txtClienteEmail.getText());
            pst.setString(5, txtClienteId.getText());

            //validação dos campos definidos como obrigatórios
            if (txtClienteNome.getText().isEmpty()
                    || txtClienteTelefone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                //a linha abaixo atualiza a tbusuarios com os dados coletados do formulario
                //a estrutura abaixo é utilizada para confirmar a alteração de dados  na tbusuarios
                int adicionado = pst.executeUpdate();
                //Caso tudo tenha ocorriodo conforme o esperado, o metodo abaixo irá limpar os campos apos a executeUpdate()
                if (adicionado > 0) {
                    System.out.println("ATUALIZADO COM SUCESSO!");
                    JOptionPane.showMessageDialog(null, "Dados do Clienete alterados com sucesso!");
                    pst = conexao.prepareStatement(sql);
                    txtClienteNome.setText(null);
                    txtClienteEndereco.setText(null);
                    txtClienteTelefone.setText(null);
                    txtClienteEmail.setText(null);
                    txtClienteId.setText(null);
                    // o método abaixo habilita o botão adicionar
                    btnUserCreate.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //O método abaixo é responsável pela remoção dos clientes da tbclientes
    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Pense bem!\n Você tem certeza dessa remoção?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbclientes where idcli=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtClienteId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                    txtClienteNome.setText(null);
                    txtClienteEndereco.setText(null);
                    txtClienteTelefone.setText(null);
                    txtClienteEmail.setText(null);
// o método abaixo habilita o botão adicionar
                    btnUserCreate.setEnabled(true);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtClienteEndereco = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnUserCreate = new javax.swing.JButton();
        btnUserUpdate = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnUserDelete = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCliPesquisar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientesTabela = new javax.swing.JTable();
        txtClienteNome = new javax.swing.JTextField();
        txtClienteTelefone = new javax.swing.JTextField();
        txtClienteEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtClienteId = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tela De Clientes");

        jLabel1.setText("*Nome:");

        jLabel2.setText("Endereço:");

        txtClienteEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteEnderecoActionPerformed(evt);
            }
        });

        jLabel3.setText("*Telefone:");

        jLabel4.setText("Email:");

        btnUserCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnUserCreate.setToolTipText("Adicionar");
        btnUserCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUserCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserCreateActionPerformed(evt);
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

        jLabel7.setText("*Campos obrigatórios");

        btnUserDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnUserDelete.setToolTipText("Deletar");
        btnUserDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUserDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserDeleteActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/pesquisar.png"))); // NOI18N

        txtCliPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliPesquisarActionPerformed(evt);
            }
        });
        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        tblClientesTabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "NOME", "ENDEREÇO", "FONE", "EMAIL"
            }
        ));
        tblClientesTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesTabelaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientesTabela);

        txtClienteNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteNomeActionPerformed(evt);
            }
        });

        txtClienteTelefone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteTelefoneActionPerformed(evt);
            }
        });

        txtClienteEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteEmailActionPerformed(evt);
            }
        });

        jLabel9.setText("*ID:");

        txtClienteId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(txtCliPesquisar)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtClienteId, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtClienteTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtClienteEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtClienteNome, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtClienteEndereco, javax.swing.GroupLayout.Alignment.LEADING)))
                            .addComponent(jLabel7))))
                .addContainerGap(59, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUserCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(btnUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(btnUserDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(108, 108, 108))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClienteNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtClienteEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtClienteTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtClienteId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtClienteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUserCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        setBounds(0, 0, 590, 486);
    }// </editor-fold>//GEN-END:initComponents

    private void txtClienteEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteEnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteEnderecoActionPerformed

    private void btnUserCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserCreateActionPerformed
        adicionar();
// TODO add your handling code here:
    }//GEN-LAST:event_btnUserCreateActionPerformed

    private void btnUserUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserUpdateActionPerformed
        alterar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserUpdateActionPerformed

    private void btnUserDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserDeleteActionPerformed
        remover();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserDeleteActionPerformed

    private void txtCliPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisarActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        pesquisar();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesTabelaMouseClicked
// evento que será usado para setar os campos da tabela (clicando com o mause).
//chamando método para setar campos
        setar_campos();
    }//GEN-LAST:event_tblClientesTabelaMouseClicked

    private void txtClienteNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteNomeActionPerformed

    private void txtClienteTelefoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteTelefoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteTelefoneActionPerformed

    private void txtClienteEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteEmailActionPerformed

    private void txtClienteIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClienteIdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUserCreate;
    private javax.swing.JButton btnUserDelete;
    private javax.swing.JButton btnUserUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblClientesTabela;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtClienteEmail;
    private javax.swing.JTextField txtClienteEndereco;
    private javax.swing.JTextField txtClienteId;
    private javax.swing.JTextField txtClienteNome;
    private javax.swing.JTextField txtClienteTelefone;
    // End of variables declaration//GEN-END:variables
}
