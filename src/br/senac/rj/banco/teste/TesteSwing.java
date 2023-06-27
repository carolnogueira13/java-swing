package br.senac.rj.banco.teste;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import br.senac.rj.banco.janelas.JanelaClassificacao;
import br.senac.rj.banco.janelas.JanelaJogador;
import br.senac.rj.banco.janelas.JanelaListaClassificacao;
import br.senac.rj.banco.janelas.JanelaListaJogadores;
import br.senac.rj.banco.janelas.JanelaListaTimes;
import br.senac.rj.banco.janelas.JanelaTime;

public class TesteSwing {

	public static void apresentarMenu() {
		// Define a janela
		JFrame.setDefaultLookAndFeelDecorated(true); 
		JFrame janelaPrincipal = new JFrame("Campeonato"); // Janela Normal
		janelaPrincipal.setTitle("Campeonato");
		janelaPrincipal.setResizable(false); // A janela não poderá ter o tamanho ajustado
		janelaPrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		janelaPrincipal.setSize(400, 300); // Define tamanho da janela
		janelaPrincipal.setLocationRelativeTo(null);
		
		// Cria uma barra de menu para a janela principal
		JMenuBar menuBar = new JMenuBar();
		
		// Adiciona a barra de menu ao frame
		janelaPrincipal.setJMenuBar(menuBar);
		
		// Define e adiciona menu na barra de menu
		JMenu menuTime = new JMenu("Times");
		JMenu menuJogador = new JMenu("Jogadores");
		JMenu menuClassificacao = new JMenu("Classificação");
		menuBar.add(menuTime);
		menuBar.add(menuJogador);
		menuBar.add(menuClassificacao);
		
		// Cria e adiciona um item simples para o menu
		JMenuItem menuItemAtualizarJogador = new JMenuItem("Atualizar");
		JMenuItem menuItemListaJogadores = new JMenuItem("Lista");
		menuJogador.add(menuItemAtualizarJogador);
		menuJogador.add(menuItemListaJogadores);
		
		JMenuItem menuItemAtualizarTime = new JMenuItem("Atualizar");
		JMenuItem menuItemListaTimes = new JMenuItem("Lista");
		menuTime.add(menuItemAtualizarTime);
		menuTime.add(menuItemListaTimes);
		
		JMenuItem menuItemAtualizarClassificacao = new JMenuItem("Atualizar");
		JMenuItem menuItemListaClassificacao = new JMenuItem("Lista");
		menuClassificacao.add(menuItemAtualizarClassificacao);
		menuClassificacao.add(menuItemListaClassificacao);
		
		
		// Criar a janela de atualização de jogadores
		JFrame janelaJogador = JanelaJogador.criarJanelaJogador();
		JFrame janelaListaJogadores = JanelaListaJogadores.criarJanelaListaJogadores();
		
		JFrame janelaTime = JanelaTime.criarJanelaTime();
		JFrame janelaListaTimes = JanelaListaTimes.criarJanelaListaTimes();
		
		
		JFrame janelaClassificao = JanelaClassificacao.criarJanelaJogador();
		JFrame janelaListaClassificacao = JanelaListaClassificacao.criarJanelaListaClassificacoes();
		
		
		// Adiciona ação para o item do menu
		menuItemAtualizarJogador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaJogador.setVisible(true);
			}
		});
		
		
		
		// Adiciona ação para o item do menu
		menuItemListaJogadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				   JanelaListaJogadores.atualizarListaJogadores();
				   janelaListaJogadores.setVisible(true);
				}
			
		});
		
		// Adiciona ação para o item do menu
		menuItemListaTimes.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        JanelaListaTimes.atualizarListaTimes();
		        janelaListaTimes.setVisible(true);
		    }
		});
		
		menuItemAtualizarClassificacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaClassificao.setVisible(true);
			}
		});
		
		menuItemListaClassificacao.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        JanelaListaClassificacao.atualizarListaClassificacao();;
		        janelaListaClassificacao.setVisible(true);
		    }
		});
		
		menuItemAtualizarTime.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				janelaTime.setVisible(true);
				
			}
		});
		
		
		janelaPrincipal.setVisible(true);
	}

	public static void main(String[] args) {
		apresentarMenu();
	}
}
