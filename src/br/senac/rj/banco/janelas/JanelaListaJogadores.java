package br.senac.rj.banco.janelas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import br.senac.rj.banco.modelo.Jogador;
import br.senac.rj.banco.modelo.Time;

public class JanelaListaJogadores {
    private static DefaultTableModel model;
    private static JTable table;

    public static JFrame criarJanelaListaJogadores() {
        JFrame janela = new JFrame("Lista de Jogadores");
        janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        janela.setSize(500, 400);
        janela.setLocation(1000, 250);
        
        JPanel painelSuperior = new JPanel();
        painelSuperior.setPreferredSize(new Dimension(1, 20));

        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
 

        JButton btnAtualizar = new JButton("Atualizar");
        Icon iconeAtualizar = new ImageIcon(JanelaListaJogadores.class.getResource("refresh.png"));
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
        
        String[] colunas = {"Id", "Nome", "Nascimento", "Time"};
        model.setColumnIdentifiers(colunas);
        
        table.setRowHeight(20);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(150);
        
        try {
        	SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        	List<Jogador> jogadores = obterListaJogadoresDoBanco();
            for (Jogador j : jogadores) {
                Object[] linha = {j.getId(), j.getNome(), formatoData.format(j.getNascimento()), j.getTime().getNome()};
                model.addRow(linha);}
        } catch (Exception error) {
        	System.out.println("Erro ao consultar os times: " + error.toString());
        }
        
    }


    public static List<Jogador> obterListaJogadoresDoBanco() {
        List<Jogador> jogadores = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "select j.id, j.nome, j.nascimento, t.id, t.nome, t.tecnico, t.estado, t.cidade from jogador j LEFT JOIN time t ON j.id_time = t.id";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica se há registros
                System.out.println("Não há times cadastrados!");
                return null;
            } else {
                // Efetua a leitura dos registros da tabela
                while (rs.next()) {
                	int id = rs.getInt("j.id");
					String nome = rs.getString("j.nome");
					Date nascimento = rs.getDate("j.nascimento");
					
					int idTime = rs.getInt("t.id");
				    String nomeTime = rs.getString("t.nome");
				    String nomeTecnico = rs.getString("t.tecnico");
				    String EstadoTime = rs.getString("t.estado");
				    String CidadeTime = rs.getString("t.cidade");
				    Time time = new Time(idTime, nomeTime, nomeTecnico, EstadoTime, CidadeTime);
				    
				    Jogador jogador = new Jogador(id, nome, nascimento, time);
				    jogadores.add(jogador);
				    
                }
                return jogadores;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao consultar jogadores: " + erro.toString());
            return null;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
}

