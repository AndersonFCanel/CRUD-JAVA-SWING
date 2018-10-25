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
 * @author ander
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    //a linha abaixo cria variável para armazenar um texto de acordo com o radio button selecionado
    private String tipo;

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisar_cliente() {
        String sql = "select idcli as ID, nomecli as NOME, "
                + "fonecli as FONE from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOSClientePesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblCliente.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setar_campos() {
        int setar = tblCliente.getSelectedRow();
        txtOSIDCli.setText(tblCliente.getModel().getValueAt(setar, 0).toString());
    }

    private void emitirOS() {
        String sql = "insert into tbos (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli)"
                + "values(?,?,?,?,?,?,?,?);";//'?','?','?','?','?','?','?','?');";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOSSituacao.getSelectedItem().toString());
            pst.setString(3, txtOSEquipamento.getText());
            pst.setString(4, txtOSDefeito.getText());
            pst.setString(5, txtOSServico.getText());
            pst.setString(6, txtOSTecnico.getText());
            pst.setString(7, txtOSValTotal.getText());
            pst.setString(8, txtOSIDCli.getText());

            if (txtOSIDCli.getText().isEmpty()
                    || (txtOSEquipamento.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso!");
                    txtOSIDCli.setText(null);
                    txtOSEquipamento.setText(null);
                    txtOSDefeito.setText(null);
                    txtOSDefeito.setText(null);
                    txtOSTecnico.setText(null);
                    txtOSValTotal.setText(null);

                }
            }

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS INVÁLIDA!");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void pesquisarOS() {
        String num_os = JOptionPane.showInputDialog("Número da OS");
        String sql = "select * from tbos where os = " + num_os;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                txtNumOS.setText(rs.getString(1));
                txtDataOS.setText(rs.getString(2));
                //Setando Radio Buttons
                String rbtTipo = rs.getString(1);
                if (rbtTipo.equals("OS")) {
                    rbtOS.setSelected(true);
                    tipo = "OS";
                } else {
                    rbtOrc.setSelected(true);
                    tipo = "Orçamento";
                }
                cboOSSituacao.setSelectedItem(rs.getString(4));
                txtOSEquipamento.setText(rs.getString(5));
                txtOSDefeito.setText(rs.getString(6));
                txtOSServico.setText(rs.getString(7));
                txtOSTecnico.setText(rs.getString(8));
                txtOSValTotal.setText(rs.getString(9));
                txtOSIDCli.setText(rs.getString(10));

                //evitando erros
                btnOSCreate.setEnabled(false);
                txtOSClientePesquisar.setEnabled(false);
                tblCliente.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "OS não cadastrada!");
            }

        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS INVÁLIDA!");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void alterar_OS() {
        String sql = "update tbos set tipo=?, situacao=?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? where os=?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOSSituacao.getSelectedItem().toString());
            pst.setString(3, txtOSEquipamento.getText());
            pst.setString(4, txtOSDefeito.getText());
            pst.setString(5, txtOSServico.getText());
            pst.setString(6, txtOSTecnico.getText());
            pst.setString(7, txtOSValTotal.getText());
            pst.setString(8, txtNumOS.getText());

            if (txtOSIDCli.getText().isEmpty()
                    || (txtOSEquipamento.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS alterada com sucesso!");
                    txtDataOS.setText(null);
                    txtNumOS.setText(null);
                    txtOSIDCli.setText(null);
                    txtOSEquipamento.setText(null);
                    txtOSDefeito.setText(null);
                    txtOSDefeito.setText(null);
                    txtOSTecnico.setText(null);
                    txtOSValTotal.setText(null);
                    //habilitar objetos
                    btnOSCreate.setEnabled(true);
                    txtOSClientePesquisar.setEnabled(true);
                    tblCliente.setVisible(true);
                }
            }
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, e2);
        }
    }

    private void excluirOS(){
     int confirma = JOptionPane.showConfirmDialog(null, "Pense bem!\n Você tem certeza dessa remoção?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "\tdelete from tbos where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNumOS.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS removida com sucesso!");
                    txtDataOS.setText(null);
                    txtNumOS.setText(null);
                    txtOSIDCli.setText(null);
                    txtOSEquipamento.setText(null);
                    txtOSDefeito.setText(null);
                    txtOSDefeito.setText(null);
                    txtOSTecnico.setText(null);
                    txtOSValTotal.setText(null);
                    //habilitar objetos
                    btnOSCreate.setEnabled(true);
                    txtOSClientePesquisar.setEnabled(true);
                    tblCliente.setVisible(true);
                
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNumOS = new javax.swing.JTextField();
        txtDataOS = new javax.swing.JTextField();
        rbtOrc = new javax.swing.JRadioButton();
        rbtOS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOSSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtOSClientePesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtOSIDCli = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCliente = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtOSEquipamento = new javax.swing.JTextField();
        txtOSDefeito = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOSServico = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtOSTecnico = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOSValTotal = new javax.swing.JTextField();
        btnOSCreate = new javax.swing.JButton();
        btnOSead = new javax.swing.JButton();
        btnOSUpdate = new javax.swing.JButton();
        btnOSDelete = new javax.swing.JButton();
        btnUserImprimir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("OS");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("N° OS");

        jLabel2.setText("Data");

        txtNumOS.setEditable(false);

        txtDataOS.setEditable(false);
        txtDataOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataOSActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOrc);
        rbtOrc.setText("Orçamento");
        rbtOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOS);
        rbtOS.setText("Ordem de Serviço");
        rbtOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtNumOS, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtOrc))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataOS)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtOS)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrc)
                    .addComponent(rbtOS)))
        );

        jLabel3.setText("Situação: ");

        cboOSSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na bancada", "Entrega OK", "Orçamento REPROVADO", "Aguardando aprovação", "Aguardando peças", "Abadonado pelo cliente", "Retornou" }));
        cboOSSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboOSSituacaoActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtOSClientePesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOSClientePesquisarActionPerformed(evt);
            }
        });
        txtOSClientePesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOSClientePesquisarKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/pesquisar.png"))); // NOI18N

        jLabel5.setText("*ID:");

        txtOSIDCli.setEditable(false);
        txtOSIDCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOSIDCliActionPerformed(evt);
            }
        });

        tblCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "NOME", "FONE"
            }
        ));
        tblCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCliente);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtOSClientePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOSIDCli, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(txtOSClientePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtOSIDCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("*Equipamento:");

        txtOSEquipamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOSEquipamentoActionPerformed(evt);
            }
        });

        txtOSDefeito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOSDefeitoActionPerformed(evt);
            }
        });

        jLabel7.setText("*Defeito:");

        txtOSServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOSServicoActionPerformed(evt);
            }
        });

        jLabel8.setText("Serviço:");

        jLabel9.setText("Técnico:");

        txtOSTecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOSTecnicoActionPerformed(evt);
            }
        });

        jLabel10.setText("Valor Total:");

        txtOSValTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOSValTotalActionPerformed(evt);
            }
        });

        btnOSCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/create.png"))); // NOI18N
        btnOSCreate.setToolTipText("Adicionar");
        btnOSCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOSCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSCreateActionPerformed(evt);
            }
        });

        btnOSead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/read.png"))); // NOI18N
        btnOSead.setToolTipText("Consultar");
        btnOSead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSead.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOSead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSeadActionPerformed(evt);
            }
        });

        btnOSUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update.png"))); // NOI18N
        btnOSUpdate.setToolTipText("Alterar");
        btnOSUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSUpdate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOSUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSUpdateActionPerformed(evt);
            }
        });

        btnOSDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/delete.png"))); // NOI18N
        btnOSDelete.setToolTipText("Deletar");
        btnOSDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnOSDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSDeleteActionPerformed(evt);
            }
        });

        btnUserImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/print.png"))); // NOI18N
        btnUserImprimir.setToolTipText("Imprimir");
        btnUserImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUserImprimir.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUserImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(cboOSSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtOSDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtOSEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtOSTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtOSValTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtOSServico, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnOSCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOSead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnOSUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnOSDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUserImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboOSSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtOSEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOSDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtOSServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtOSTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtOSValTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOSead, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOSCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOSUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOSDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        setBounds(0, 0, 644, 495);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataOSActionPerformed

    private void txtOSClientePesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSClientePesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSClientePesquisarActionPerformed

    private void txtOSIDCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSIDCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSIDCliActionPerformed

    private void txtOSEquipamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSEquipamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSEquipamentoActionPerformed

    private void txtOSDefeitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSDefeitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSDefeitoActionPerformed

    private void txtOSServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSServicoActionPerformed

    private void txtOSTecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSTecnicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSTecnicoActionPerformed

    private void txtOSValTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOSValTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSValTotalActionPerformed

    private void btnOSCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSCreateActionPerformed
        emitirOS();
    }//GEN-LAST:event_btnOSCreateActionPerformed

    private void btnOSeadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSeadActionPerformed
        pesquisarOS();
    }//GEN-LAST:event_btnOSeadActionPerformed

    private void btnOSUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSUpdateActionPerformed
alterar_OS();
    }//GEN-LAST:event_btnOSUpdateActionPerformed

    private void btnOSDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSDeleteActionPerformed
excluirOS();
    }//GEN-LAST:event_btnOSDeleteActionPerformed

    private void btnUserImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserImprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUserImprimirActionPerformed

    private void txtOSClientePesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOSClientePesquisarKeyReleased
        pesquisar_cliente();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOSClientePesquisarKeyReleased

    private void tblClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteMouseClicked
        setar_campos();
        // TODO add your handling code here:
    }//GEN-LAST:event_tblClienteMouseClicked

    private void rbtOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcActionPerformed
        //atribuindo um tipo a variável tipo se selecionado
        tipo = "Orçamento";
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtOrcActionPerformed

    private void rbtOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOSActionPerformed
        tipo = "OS";
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtOSActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        //ao abrir o form marcar o radio button orçamento
        rbtOrc.setSelected(true);
        tipo = "Orçamento";
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameOpened

    private void cboOSSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOSSituacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboOSSituacaoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOSCreate;
    private javax.swing.JButton btnOSDelete;
    private javax.swing.JButton btnOSUpdate;
    private javax.swing.JButton btnOSead;
    private javax.swing.JButton btnUserImprimir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOSSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOS;
    private javax.swing.JRadioButton rbtOrc;
    private javax.swing.JTable tblCliente;
    private javax.swing.JTextField txtDataOS;
    private javax.swing.JTextField txtNumOS;
    private javax.swing.JTextField txtOSClientePesquisar;
    private javax.swing.JTextField txtOSDefeito;
    private javax.swing.JTextField txtOSEquipamento;
    private javax.swing.JTextField txtOSIDCli;
    private javax.swing.JTextField txtOSServico;
    private javax.swing.JTextField txtOSTecnico;
    private javax.swing.JTextField txtOSValTotal;
    // End of variables declaration//GEN-END:variables
}
