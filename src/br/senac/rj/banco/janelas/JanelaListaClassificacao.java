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

import br.senac.rj.banco.modelo.Classificacao;

public class JanelaListaClassificacao {
	private static DefaultTableModel model;
    private static JTable table;

    public static JFrame criarJanelaListaClassificacoes() {
        JFrame janela = new JFrame("Lista de Classificacao de Times");
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
                atualizarListaClassificacao();
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

    public static void atualizarListaClassificacao() {
        model.setRowCount(0); // Limpa os dados existentes na tabela
        
        String[] colunas = {"Classificacao", "Time", "Pontuação"};
        model.setColumnIdentifiers(colunas);
        
        table.setRowHeight(20);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(50);
        
        try {
        	List<Classificacao> classificacoes = Classificacao.obterListaClassificacaoDoBanco();
        	int posicao = 0;
            for (Classificacao classificacao :classificacoes) {
            	posicao++;
                Object[] linha = {posicao + " º", classificacao.getTime().getNome(), classificacao.getPontuacao()};
                model.addRow(linha);}
        } catch (Exception error) {
        	System.out.println("Erro ao consultar as classificações dos times: " + error.toString());
        }
        
    }


    
}
