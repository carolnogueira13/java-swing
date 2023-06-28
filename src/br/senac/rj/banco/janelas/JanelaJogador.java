package br.senac.rj.banco.janelas;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.MaskFormatter;

import br.senac.rj.banco.modelo.Jogador;
import br.senac.rj.banco.modelo.Time;
/**
 * A classe JanelaJogador representa a janela de atualização de um jogador
 * 
 */
public class JanelaJogador {
	
	private static JComboBox<Time> comboTimes; // Selecionar um time
	/**
	 * Cria e retorna a instância de JFrame da janela de atualização do jogador
	 * @return O JFrame da janela de atualização do jogador
	 */
	
	public static JFrame criarJanelaJogador() {
		// Define a janela
		JFrame janelaJogador = new JFrame("Atualiza��o do jogador"); // Janela Normal
		janelaJogador.setResizable(false); // A janela n�o poder� ter o tamanho ajustado
		janelaJogador.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		janelaJogador.setSize(500, 300); // Define tamanho da janela
		janelaJogador.setLocation(50, 250);
		
		// Define o layout da janela
		Container caixa = janelaJogador.getContentPane();
		caixa.setLayout(null);
		
		// Define os labels dos campos
		JLabel labelId = new JLabel("Id: ");
		JLabel labelNome = new JLabel("Nome: ");
		JLabel labelNascimento = new JLabel("Data nascimento:");
		
		// Posiciona os labels na janela
		labelId.setBounds(50, 40, 100, 20); // coluna, linha, largura, tamanho
		labelNome.setBounds(50, 80, 150, 20); // coluna, linha, largura, tamanho
		labelNascimento.setBounds(50, 120, 100, 20); // coluna, linha, largura, tamanho
		
		// Mascara para o campo de data
		String formatoMascara = "##/##/####";
		MaskFormatter mascara;
		JFormattedTextField jFormattedTextNascimento = null;
		try {
			mascara = new MaskFormatter(formatoMascara);
			jFormattedTextNascimento = new JFormattedTextField(mascara);
			jFormattedTextNascimento.setBounds(180, 120, 250, 20);
			janelaJogador.add(jFormattedTextNascimento);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		
		// Define os input box
		JTextField jTextId = new JTextField();
		JTextField jTextNome = new JTextField();
		
		// Define se os campos est�o habilitados ou n�o no in�cio
		jTextId.setEditable(true);
		jTextNome.setEnabled(false);
		jFormattedTextNascimento.setEnabled(false);
		
		// Posiciona os input box
		jTextId.setBounds(180, 40, 50, 20);
		jTextNome.setBounds(180, 80, 150, 20);
		
		
		// Adiciona os r�tulos e os input box na janela
		janelaJogador.add(labelId);
		janelaJogador.add(labelNome);
		janelaJogador.add(labelNascimento);
		janelaJogador.add(jTextId);
		janelaJogador.add(jTextNome);
		
		
		//ComboBox de Times 
		JLabel labelTime = new JLabel("Time: ");
		labelTime.setBounds(50,160, 200, 20 );
		comboTimes = new JComboBox<Time>();
	    comboTimes.setBounds(180, 160, 250, 20);
	    comboTimes.setEnabled(false);
	    janelaJogador.add(labelTime);
        janelaJogador.add(comboTimes);
        
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
		botaoConsultar.setBounds(230, 40, 100, 20);
		janelaJogador.add(botaoConsultar);
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 200, 100, 20);
		botaoGravar.setEnabled(false);
		janelaJogador.add(botaoGravar);
		JButton botaoLimpar = new JButton("Limpar");
		botaoLimpar.setBounds(350, 200, 100, 20);
		janelaJogador.add(botaoLimpar);
		JButton botaoDeletar = new JButton("Deletar");
		botaoDeletar.setBounds(200, 200, 100, 20);
		botaoDeletar.setEnabled(false);
		janelaJogador.add(botaoDeletar);
	
		
		// Define objeto jogador para pesquisar no banco de dados
		Jogador jogador = new Jogador();
		
		final JFormattedTextField jFormattedTextNascimentoFinal = jFormattedTextNascimento;
		
		// Adicionou um Listener na janela, nesse caso para quando estiver fechando simular um clique no bot�o limpar para limpar a janela
		janelaJogador.addWindowListener(new WindowListener() {
			
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
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(jTextId.getText());
					botaoGravar.setEnabled(true);
					botaoDeletar.setEnabled(true);
					Time time;
					if (!jogador.consultarJogador(id)) {
						JOptionPane.showMessageDialog(janelaJogador, "Jogador n�o encontrado!");
						jTextNome.setText("");
						jFormattedTextNascimentoFinal.setText("");
						time = null;
					}
					else {
						jTextId.setText(String.valueOf(jogador.getId()));
						jTextNome.setText(jogador.getNome());
						Date dataNascimento = jogador.getNascimento();
						SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
						String dataFormatada2 = formatoData.format(dataNascimento);
						jFormattedTextNascimentoFinal.setText(dataFormatada2);
						time = jogador.getTime();
					}
					
					System.out.println(time);
					jTextNome.setEnabled(true);
					jTextId.setEnabled(false);
					comboTimes.setSelectedItem(time);					
					jFormattedTextNascimentoFinal.setEnabled(true);
					comboTimes.setEnabled(true);
					botaoConsultar.setEnabled(false);
					jTextNome.requestFocus();
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janelaJogador,
							"Preencha os campos restantes de forma correta!");
				}
			}
		});
		
		
		botaoGravar.addActionListener(new ActionListener() {
			/**
		     * Realiza a ação quando o evento actionPerformed é acionado.
		     * Exibe uma caixa de diálogo de confirmação para atualização.
		     * Se a resposta for afirmativa, obtém os valores dos campos nome e data de nascimento,
		     * converte a data para o formato desejado e obtém o time selecionado no combobox.
		     * Realiza as verificações e chama os métodos apropriados do objeto jogador para cada caso,
		     * exibindo mensagens de sucesso ou erro correspondentes.
		     * Limpa os campos e atualiza a exibição conforme necessário.
		     * @param e O evento ActionEvent acionado.
		     */
			public void actionPerformed(ActionEvent e) {

				int resposta = JOptionPane.showConfirmDialog(janelaJogador, "Deseja atualizar?", "Confirma��o",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					String nome = jTextNome.getText().trim();
					String dataString = jFormattedTextNascimentoFinal.getText();
			        String formato = "dd/MM/yyyy";
			        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			        Date data = null;
			        try {
			            data = dateFormat.parse(dataString);
			            System.out.println("Data convertida: " + data);
			            Time time = (Time) comboTimes.getSelectedItem();
				        if (nome.length() == 0 && data != null) {
				        	JOptionPane.showMessageDialog(janelaJogador,
									"Preencha o campos restante nome de forma correta!");
							jTextNome.requestFocus();
						} else {
						    int id = Integer.parseInt(jTextId.getText());
						    if (time == null) {
						        if (!jogador.consultarJogador(id)) {
						            if (!jogador.cadastrarJogador(nome, data)) {
						                JOptionPane.showMessageDialog(janelaJogador, "Erro na inclus�o do jogador!");
						            } else {
						                JOptionPane.showMessageDialog(janelaJogador, "Inclus�o realizada para jogador!");
						                botaoLimpar.doClick();
						            }
						        } else {
						            if (!jogador.atualizaJogador(nome, data)) {
						                JOptionPane.showMessageDialog(janelaJogador, "Erro na atualiza��o do jogador!");
						            } else {
						                JOptionPane.showMessageDialog(janelaJogador, "Altera��o realizada para jogador!");
						                botaoLimpar.doClick();
						            }
						        }
						    } else {
						        // time n�o � nulo
						        if (!jogador.consultarJogador(id)) {
						            if (!jogador.cadastrarJogador(nome, data, time))
						                JOptionPane.showMessageDialog(janelaJogador, "Erro na inclus�o do jogador!");
						            else {
						                JOptionPane.showMessageDialog(janelaJogador, "Inclus�o realizada!");
						                botaoLimpar.doClick();
						            }
						        } else {
						            if (!jogador.atualizaJogador(nome, data, time))
						                JOptionPane.showMessageDialog(janelaJogador, "Erro na atualiza��o do jogador!");
						            else {
						                JOptionPane.showMessageDialog(janelaJogador, "Altera��o realizada!");
						                botaoLimpar.doClick();
						            }
						        }
						    }
						}
			        } catch (ParseException e2) {
			        	JOptionPane.showMessageDialog(janelaJogador,
								"Preencha o campo restantes data de forma correta!");
			            System.out.println("Erro ao converter a data: " + e2.getMessage());
			            
			        }	

			}
			}});
		
		
		botaoDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int resposta = JOptionPane.showConfirmDialog(janelaJogador, "Deseja deletar?", "Confirma��o",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {
					int id = Integer.parseInt(jTextId.getText());
					if (!jogador.consultarJogador(id)) {
						JOptionPane.showMessageDialog(janelaJogador, "Impossivel excluir jogador n�o cadastrado!");
					} else {
						id = jogador.getId();
						if (!jogador.deletarJogador(id))
							JOptionPane.showMessageDialog(janelaJogador, "Erro ao excluir o jogador!");
						else {
							JOptionPane.showMessageDialog(janelaJogador, "Exclus�o realizada!");
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
				jTextNome.setText(""); // Limpar campo
				jFormattedTextNascimentoFinal.setText(""); // Limpar campo
				comboTimes.setSelectedItem(null);
				comboTimes.setEnabled(false);
				jTextId.setEnabled(true);
				jTextNome.setEnabled(false);
				jFormattedTextNascimentoFinal.setEnabled(false);
				botaoConsultar.setEnabled(true);
				botaoGravar.setEnabled(false);
				botaoDeletar.setEnabled(false);
				jTextId.requestFocus(); // Colocar o foco em um campo
			}
		}
		);
		
		return janelaJogador;
	}
	/**
     * Atualiza o combobox de times.
     * Obtém a lista de times do banco de dados e atualiza o combobox com os times encontrados.
     * Adiciona um item nulo como opção inicial.
     * Realiza a validação e repintura do combobox.
     * Exibe mensagens de erro em caso de falha na consulta.
     */
	public static void atualizarComboboxTimes() {
		try {
			List<Time> listaTimes = Time.obterListaTimesDoBanco();
	        comboTimes.removeAllItems();
	        comboTimes.addItem(null);
	        for (Time time : listaTimes) {
	            comboTimes.addItem(time);
	        }
	        comboTimes.revalidate();
	        comboTimes.repaint();
		} catch (Exception e) {
			System.out.println("Erro ao consultar os times: " + e.toString());
		}
        
    }
	
}

