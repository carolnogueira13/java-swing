package br.senac.rj.banco.janelas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import br.senac.rj.banco.modelo.Time;
/**
 * A classe JanelaListaTimes representa a janela de exibição da lista de times
 * 
 */
public class JanelaListaTimes {
    private static DefaultTableModel model; // Modelo de tabela para armazenar os dados dos times
    private static JTable table; // Tabela para exibir os times
    /**
     * Cria e retorna a instância de JFrame da janela de lista de times
     * @return O JFrame da janela de lista de times
     */

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
    /**
     * Atualiza a listas de times na tabela
     */
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
        	List<Time> times = Time.obterListaTimesDoBanco();
            for (Time time :times) {
                Object[] linha = {time.getId(), time.getNome(), time.getTecnico(), time.getEstado(), time.getCidade()};
                model.addRow(linha);}
        } catch (Exception error) {
        	System.out.println("Erro ao consultar os times: " + error.toString());
        }
        
    }


    
}

