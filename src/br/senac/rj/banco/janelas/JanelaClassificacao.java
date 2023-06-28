package br.senac.rj.banco.janelas;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import br.senac.rj.banco.modelo.Classificacao;
import br.senac.rj.banco.modelo.Time;
/**
 * 
 * A classe JanelaClassificacao representa a janela de atualização 
 * da classificação de um time
 *
 */
public class JanelaClassificacao {
	
	private static JComboBox<Time> comboTimes; // selecionar um time
	/**
	 * Cria e retorna a instância de JFrame da janela de atualização da classificação do time
	 * @return O JFrame da janela de atualização da classificação do time.
	 */
	
	public static JFrame criarJanelaJogador() {
		// Define a janela
		JFrame janelaClassificacao = new JFrame("Atualiza��o da classifica��o do time"); // Janela Normal
		janelaClassificacao.setResizable(false); // A janela n�o poder� ter o tamanho ajustado
		janelaClassificacao.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		janelaClassificacao.setSize(500, 400); // Define tamanho da janela
		janelaClassificacao.setLocation(50, 250);
		
		// Define o layout da janela
		Container caixa = janelaClassificacao.getContentPane();
		caixa.setLayout(null);
		
		// Define os labels dos campos
		JLabel labelId = new JLabel("Id: ");
		JLabel labelTime = new JLabel("Time: ");
		JLabel labelPontuacao = new JLabel("Pontua��o:");
		JLabel labelVitorias = new JLabel("Vit�rias:");
		JLabel labelDerrotas = new JLabel("Derrotas:");
		JLabel labelEmpates = new JLabel("Empates:");
		
		// Posiciona os labels na janela
		labelId.setBounds(50, 40, 100, 20); // coluna, linha, largura, tamanho
		labelTime.setBounds(50, 80, 150, 20); // coluna, linha, largura, tamanho
		labelPontuacao.setBounds(50, 120, 100, 20); // coluna, linha, largura, tamanho
		labelVitorias.setBounds(50, 160, 100, 20);
		labelDerrotas.setBounds(50, 200, 100, 20);
		labelEmpates.setBounds(50, 240, 100, 20);

		
		// Define os input box
		JTextField jTextId = new JTextField();
		JTextField jTextPontuacao = new JTextField();
		JTextField jTextVitorias = new JTextField();
		JTextField jTextDerrotas = new JTextField();
		JTextField jTextEmpates = new JTextField();
		
		// Define se os campos est�o habilitados ou n�o no in�cio
		jTextId.setEditable(false);
		jTextPontuacao.setEnabled(false);
		jTextVitorias.setEnabled(false);
		jTextDerrotas.setEnabled(false);
		jTextEmpates.setEnabled(false);
		
		// Posiciona os input box
		jTextId.setBounds(180, 40, 50, 20);
		jTextPontuacao.setBounds(180, 120, 50, 20);
		jTextVitorias.setBounds(180, 160, 50, 20);
		jTextDerrotas.setBounds(180, 200, 50, 20);
		jTextEmpates.setBounds(180, 240, 50, 20);
		
		
		// Adiciona os r�tulos e os input box na janela
		janelaClassificacao.add(labelId);
		janelaClassificacao.add(labelTime);
		janelaClassificacao.add(labelPontuacao);
		janelaClassificacao.add(labelVitorias);
		janelaClassificacao.add(labelDerrotas);
		janelaClassificacao.add(labelEmpates);
		janelaClassificacao.add(jTextId);
		janelaClassificacao.add(jTextPontuacao);
		janelaClassificacao.add(jTextVitorias);
		janelaClassificacao.add(jTextDerrotas);
		janelaClassificacao.add(jTextEmpates);
		
		
		//ComboBox de Times 
		comboTimes = new JComboBox<Time>();
	    comboTimes.setBounds(180, 80, 150, 20);
	    comboTimes.setEnabled(true);
	    janelaClassificacao.add(labelTime);
        janelaClassificacao.add(comboTimes);
        

        // Para quando abrir o ComboBox chamar o m�todo para atualizar o ComboBox 
        comboTimes.addPopupMenuListener(new PopupMenuListener() {
			
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				atualizarComboboxTimes();
				
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		// Define bot�es e a localiza��o deles na janela
		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(350, 80, 100, 20);
		janelaClassificacao.add(botaoConsultar);
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 300, 100, 20);
		botaoGravar.setEnabled(false);
		janelaClassificacao.add(botaoGravar);
		JButton botaoLimpar = new JButton("Limpar");
		botaoLimpar.setBounds(350, 300, 100, 20);
		janelaClassificacao.add(botaoLimpar);
		JButton botaoDeletar = new JButton("Deletar");
		botaoDeletar.setBounds(200, 300, 100, 20);
		botaoDeletar.setEnabled(false);
		janelaClassificacao.add(botaoDeletar);
	
		
		// Define objeto classifica��o para pesquisar no banco de dados
		Classificacao classificacao = new Classificacao();
		
		
		// Adicionou um Listener na janela, nesse caso para quando estiver fechando simular um clique no bot�o limpar para limpar a janela 
		janelaClassificacao.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				botaoLimpar.doClick();
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		// Define a��es dos bot�es
		botaoConsultar.addActionListener(new ActionListener() {
			/**
			 * Método executado quando o botão de consultar é acionado.
			 * Consulta a classificação de um time selecionado
			 * e preenche os campos de texto com as informações encontradas.
			 * @param e o evento de ação que acionou o método
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					Time time = (Time) comboTimes.getSelectedItem();
					botaoGravar.setEnabled(true);
					botaoDeletar.setEnabled(true);
					if (time.equals(null)) {
						JOptionPane.showMessageDialog(janelaClassificacao, "Preencha o campo faltante");
					} else {
							if (!classificacao.consultarClassificacao(time)) {
								JOptionPane.showMessageDialog(janelaClassificacao, "Time n�o encontrado na classifica��o!");
								jTextPontuacao.setText("");
								jTextEmpates.setText("");
								jTextDerrotas.setText("");
								jTextVitorias.setText("");
								
							}
							else {
								jTextId.setText(String.valueOf(classificacao.getId()));
								jTextPontuacao.setText(String.valueOf(classificacao.getPontuacao()));
								jTextEmpates.setText(String.valueOf(classificacao.getEmpates()));
								jTextDerrotas.setText(String.valueOf(classificacao.getDerrotas()));
								jTextVitorias.setText(String.valueOf(classificacao.getVitorias()));
							}
						}
					System.out.println(time);
					jTextPontuacao.setEnabled(true);
					jTextVitorias.setEnabled(true);
					jTextDerrotas.setEnabled(true);
					jTextEmpates.setEnabled(true);
					comboTimes.setSelectedItem(time);
					comboTimes.setEnabled(false);
					botaoConsultar.setEnabled(false);
					jTextPontuacao.requestFocus();
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janelaClassificacao,
							"Preencha os campos faltantes!");
				}
			}
		});
		
		
		botaoGravar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				int resposta = JOptionPane.showConfirmDialog(janelaClassificacao, "Deseja atualizar?", "Confirma��o",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					int pontuacao = Integer.parseInt(jTextPontuacao.getText());
					int derrotas = Integer.parseInt(jTextDerrotas.getText());
					int vitorias = Integer.parseInt(jTextVitorias.getText());
					int empates = Integer.parseInt(jTextEmpates.getText());
			        Time time = (Time) comboTimes.getSelectedItem();
					if ( time.equals(null) ) {
						JOptionPane.showMessageDialog(janelaClassificacao, "Preencha o campo faltante");
						jTextPontuacao.requestFocus();
					} else {
						if (!classificacao.consultarClassificacao(time)) {
							if (!classificacao.cadastrarClassificacao(time, pontuacao, vitorias, derrotas, empates))
								JOptionPane.showMessageDialog(janelaClassificacao, "Erro na inclus�o da classifica��o!");
							else {
								JOptionPane.showMessageDialog(janelaClassificacao, "Inclus�o realizada!");
								botaoLimpar.doClick();
							}
								
						} else {
							if (!classificacao.atualizaClassificacao(time, pontuacao, vitorias, derrotas, empates))
								JOptionPane.showMessageDialog(janelaClassificacao, "Erro na atualiza��o da classifica��o do time!");
							else {
								JOptionPane.showMessageDialog(janelaClassificacao, "Altera��o realizada!");
								botaoLimpar.doClick();
							}
						}
					}
				}
			}
		});
		
		
		botaoDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int resposta = JOptionPane.showConfirmDialog(janelaClassificacao, "Deseja deletar?", "Confirma��o",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					Time time = (Time) comboTimes.getSelectedItem();
					if (!classificacao.consultarClassificacao(time)) {
						JOptionPane.showMessageDialog(janelaClassificacao, "Impossivel excluir time ainda n�o incluido na classifica��o!");
					} else {
						if (!classificacao.deletarClassificacao(time))
							JOptionPane.showMessageDialog(janelaClassificacao, "Erro ao excluir o time da classifica��o!");
						else {
							JOptionPane.showMessageDialog(janelaClassificacao, "Exclus�o realizada!");
							botaoLimpar.doClick();
						}
						}
					}
				}
			}
		);
		
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextId.setText(""); // Limpar campo
				jTextPontuacao.setText("");
				jTextEmpates.setText("");
				jTextDerrotas.setText("");
				jTextVitorias.setText("");
				comboTimes.setSelectedItem(null);
				comboTimes.setEnabled(true);
				jTextPontuacao.setEnabled(false);
				jTextVitorias.setEnabled(false);
				jTextDerrotas.setEnabled(false);
				jTextEmpates.setEnabled(false);
				botaoConsultar.setEnabled(true);
				botaoGravar.setEnabled(false);
				botaoDeletar.setEnabled(false);
				jTextId.requestFocus(); // Colocar o foco em um campo
			}
		}
		);
		
		return janelaClassificacao;
	}
	/**
	 * Atualiza o combobox de times com a lista de times
	 *  obtida do banco de dados.
	 *  Remove todos os itens existentes no combobox,
	 *  obtém a lista de times do banco de dados
	 *  e adiciona cada time como um item no combobox
	 *  
	 */
	public static void atualizarComboboxTimes() {
		try {
			List<Time> listaTimes = Time.obterListaTimesDoBanco();
	        comboTimes.removeAllItems();
	        for (Time time : listaTimes) {
	            comboTimes.addItem(time);
	        }
	        comboTimes.revalidate();
	        comboTimes.repaint();
	        comboTimes.setSelectedItem(null);
		} catch (Exception e) {
			System.out.println("Erro ao consultar os times: " + e.toString());
		}
        
    }
	
}

