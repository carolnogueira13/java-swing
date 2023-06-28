package br.senac.rj.banco.janelas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
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

import br.senac.rj.banco.modelo.Jogador;
/**
 * A classe JanelaListaJogadores representa
 * a janela de exibição da lista de jogadores
 * 
 *
 */
public class JanelaListaJogadores {
    private static DefaultTableModel model;  // Modelo de tabela para armazenar os dados dos jogadores
    private static JTable table; // Tabela para exibir os jogadores
/**
 * Cria e retorna a instância de JFrame da janela de lista de jogadores
 * @return O JFrame da janela de lista de jogadores
 */
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
                atualizarListaJogadores();
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
    
    /**
     * Atualiza a lista de jogadores na tabela
     */
    public static void atualizarListaJogadores() {
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
        	List<Jogador> jogadores = Jogador.obterListaJogadoresDoBanco();
            for (Jogador j : jogadores) {
                Object[] linha = {j.getId(), j.getNome(), formatoData.format(j.getNascimento()), j.getTime().getNome()};
                model.addRow(linha);}
        } catch (Exception error) {
        	System.out.println("Erro ao consultar os times: " + error.toString());
        }
        
    }


    
}

