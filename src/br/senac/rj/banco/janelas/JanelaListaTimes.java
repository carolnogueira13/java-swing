package br.senac.rj.banco.janelas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import br.senac.rj.banco.modelo.Conexao;
import br.senac.rj.banco.modelo.Time;

public class JanelaListaTimes {
    private static DefaultTableModel model;
    private static JTable table;

    public static JFrame criarJanelaListaTimes() {
        JFrame janela = new JFrame("Lista de Times");
        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        janela.setSize(500, 400);
        janela.setLocation(1000, 250);
        
        JPanel painelSuperior = new JPanel();
        painelSuperior.setPreferredSize(new Dimension(1, 20));

        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
 

        JButton btnAtualizar = new JButton("Atualizar");
        Icon iconeAtualizar = new ImageIcon(JanelaListaTimes.class.getResource("refresh.png"));
        btnAtualizar.setIcon(iconeAtualizar);
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarListaTimes();
            }
        });

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnAtualizar);

        janela.setLayout(new BorderLayout());
        janela.add(painelSuperior, BorderLayout.NORTH);
        janela.add(scrollPane, BorderLayout.CENTER);
        janela.add(painelBotoes, BorderLayout.SOUTH);
        

        return janela;
    }

    public static void atualizarListaTimes() {
        model.setRowCount(0); // Limpa os dados existentes na tabela
        
        String[] colunas = {"Id", "Nome", "Tecnico", "Estado(UF)", "Cidade"};
        model.setColumnIdentifiers(colunas);
        
        table.setRowHeight(20);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(300);
        columnModel.getColumn(3).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(300);
        
        try {
        	List<Time> times = obterListaTimesDoBanco();
            for (Time time :times) {
                Object[] linha = {time.getId(), time.getNome(), time.getTecnico(), time.getEstado(), time.getCidade()};
                model.addRow(linha);}
        } catch (Exception error) {
        	System.out.println("Erro ao consultar os times: " + error.toString());
        }
        
    }


    public static List<Time> obterListaTimesDoBanco() {
        List<Time> times = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "select * from time";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica se há registros
                System.out.println("Não há times cadastrados!");
                return null;
            } else {
                // Efetua a leitura dos registros da tabela
                while (rs.next()) {
                	int id = rs.getInt("id");
                	String nome = rs.getString("nome");
                    String tecnico = rs.getString("tecnico");
                    String estado = rs.getString("estado");
                    String cidade = rs.getString("cidade");
                    Time time = new Time(id, nome, tecnico, estado, cidade);
                    times.add(time);
                }
                return times;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao consultar estudantes: " + erro.toString());
            return null;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
}

