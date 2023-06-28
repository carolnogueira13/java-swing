 package br.senac.rj.banco.janelas;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import br.senac.rj.banco.modelo.Jogador;
import br.senac.rj.banco.modelo.Time;
/**
 * A classe JanelaTime representa a janela de cadastro e edição de times
 * 
 *
 */
public class JanelaTime {
	private static JComboBox<String> comboEstados; // ComboBox para selecionar estados
	private static DefaultTableModel modelJanelaTime; // Modelo de tabela para armazenar os dados dos times
    private static JTable tableJanelaTime; // Tabela para exibir os times
    /**
     * Cria e retorna a instância de JFrame da janela de cadastro e edição de times
     * @return O JFrame da janela de cadastro e edição de times
     */
	public static JFrame criarJanelaTime() {
		// Criação do JFrame
		JFrame janelaTime = new JFrame("Janela Time"); 
		janelaTime.setResizable(false); 
		janelaTime.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		janelaTime.setSize(500, 700); 
		janelaTime.setLocation(50, 250);

		// Criação e configuração dos componentes da janela
		Container caixa = janelaTime.getContentPane();
		caixa.setLayout(null);
		
		JLabel labelId = new JLabel("Id: ");
		JLabel labelNome = new JLabel("Nome: ");
		JLabel labelTecnico = new JLabel("Técnico: ");
		JLabel labelCidade = new JLabel("Cidade: ");
		JLabel labelEstado = new JLabel("Estado: ");
		// coluna, linha, largura, tamanho
		labelId.setBounds(50, 30, 100, 20);
		labelNome.setBounds(50, 60, 100, 20);
		labelCidade.setBounds(50, 90, 100, 20);
		labelEstado.setBounds(50, 120, 100, 20);
		labelTecnico.setBounds(50, 150, 100, 20);
		
		
		JTextField jTextId = new JTextField();
		JTextField jTextNome = new JTextField();
		JTextField jTextTecnico = new JTextField();
		JTextField jTextCidade = new JTextField();
//		JTextField jTextEstado = new JTextField();
		comboEstados = new JComboBox<String>();
		
		
		jTextId.setBounds(120, 30, 100, 20);
		jTextNome.setBounds(120, 60, 100, 20);
		jTextCidade.setBounds(120, 90, 100, 20);
		comboEstados.setBounds(120, 120, 100, 20);
		jTextTecnico.setBounds(120, 150, 100, 20);
		
		jTextId.setEnabled(true);
		jTextNome.setEnabled(false);
		jTextTecnico.setEnabled(false);
		jTextCidade.setEnabled(false);
		comboEstados.setEnabled(false);
		
		
		JButton botaoConsultar = new JButton("Consultar");
		JButton botaoGravar = new JButton("Gravar");
		JButton botaoDeletar = new JButton("Deletar");
		JButton botaoLimpar = new JButton("Limpar");
		
		botaoConsultar.setBounds(250, 30, 100, 20);
		botaoGravar.setBounds(50, 210, 100, 20);
		botaoDeletar.setBounds(160, 210, 100, 20);
		botaoLimpar.setBounds(270, 210, 100, 20);
		
		botaoGravar.setEnabled(false);
		botaoDeletar.setEnabled(false);
		
		modelJanelaTime = new DefaultTableModel();
		tableJanelaTime = new JTable(modelJanelaTime);
		JScrollPane scrollPaneJanelaTime = new JScrollPane(tableJanelaTime);
		
		scrollPaneJanelaTime.setBounds(10, 300, 470, 350);
		String[] colunas = {"Id", "Nome", "Data Nascimento"};
        modelJanelaTime.setColumnIdentifiers(colunas);
        tableJanelaTime.setRowHeight(20);
        
        
        TableColumnModel columnModelJanelaTime = tableJanelaTime.getColumnModel();
        columnModelJanelaTime.getColumn(0).setPreferredWidth(10);
        columnModelJanelaTime.getColumn(1).setPreferredWidth(150);
        columnModelJanelaTime.getColumn(2).setPreferredWidth(150);

		janelaTime.add(labelId);
		janelaTime.add(labelNome);
		janelaTime.add(labelCidade);
		janelaTime.add(labelEstado);
		janelaTime.add(labelTecnico);
		janelaTime.add(jTextId);
		janelaTime.add(jTextNome);
		janelaTime.add(jTextCidade);
		janelaTime.add(comboEstados);
		janelaTime.add(jTextTecnico);
		janelaTime.add(botaoConsultar);
		janelaTime.add(botaoGravar);
		janelaTime.add(botaoDeletar);
		janelaTime.add(botaoLimpar);
		janelaTime.add(scrollPaneJanelaTime);
		
		Time time = new Time();
		
		atualizarComboboxEstados();
		
		janelaTime.addWindowListener(new WindowListener() {
			
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
				modelJanelaTime.setRowCount(0);
				
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
		botaoConsultar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(jTextId.getText());
					if (!time.consultarTime(id)) {
						JOptionPane.showMessageDialog(janelaTime, "Time n�o encontrado!");
						jTextId.setText("");
					}else {
						jTextId.setText(String.valueOf(time.getId()));
						jTextNome.setText(time.getNome());
						if(time.getTecnico() == null) {
							jTextTecnico.setText("");
						}
						else {
							jTextTecnico.setText(time.getTecnico());
						}
						jTextCidade.setText(time.getCidade());
						comboEstados.setSelectedItem(time.getEstado());
						atualizarListaJogadores(time);
						
					}
					botaoGravar.setEnabled(true);
					botaoDeletar.setEnabled(true);
					jTextId.setEnabled(false);
					jTextNome.setEnabled(true);
					jTextCidade.setEnabled(true);
					comboEstados.setEnabled(true);
					jTextTecnico.setEnabled(true);
					
					
				}catch (Exception erro) {
					JOptionPane.showMessageDialog(janelaTime,"Preencha os campos restantes de forma correta!");
				
				}
			}
		});
		
		botaoGravar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jTextId.getText().isBlank()) {
					if(jTextTecnico.getText().isBlank()) {
						if(time.cadastrarTime(jTextNome.getText(), jTextCidade.getText(), comboEstados.getSelectedItem().toString())) {
							JOptionPane.showMessageDialog(janelaTime,"Time cadastrado com sucesso");
							botaoLimpar.doClick();
						}else {
							JOptionPane.showMessageDialog(janelaTime,"Falha ao cadastrar time");
						}
						
					}
					else {
						if(time.cadastrarTime(jTextNome.getText(),jTextTecnico.getText(), jTextCidade.getText(), comboEstados.getSelectedItem().toString())) {
							JOptionPane.showMessageDialog(janelaTime,"Time cadastrado com sucesso");
							botaoLimpar.doClick();
						}else {
							JOptionPane.showMessageDialog(janelaTime,"Falha ao cadastrar time");
						}
						
					}
				}else {
					time.setNome(jTextNome.getText());
					time.setTecnico(jTextTecnico.getText());
					time.setCidade(jTextCidade.getText());
					time.setEstado(comboEstados.getSelectedItem().toString());
					if(time.atualizarTime()) {
						JOptionPane.showMessageDialog(janelaTime,"Time atualizado com sucesso");
						botaoLimpar.doClick();
					}else {
						JOptionPane.showMessageDialog(janelaTime,"Falha ao atualizar time");
					}
					
				}
			}
		});
		botaoDeletar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int resposta = JOptionPane.showConfirmDialog(janelaTime, "Deseja realmente deletar o time?", "Confirma��o", JOptionPane.YES_NO_OPTION); 
				if(resposta == JOptionPane.YES_OPTION) {
					if(time.deletarTime()) {
						JOptionPane.showMessageDialog(janelaTime,"Time deletado com sucesso");
						botaoLimpar.doClick();
					}else {
						JOptionPane.showMessageDialog(janelaTime,"Falha ao deletar time");
					}
				}else {
					JOptionPane.showMessageDialog(janelaTime, "Opera��o cancelada");
				}
			}
		});
		botaoLimpar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jTextId.setEnabled(true);
				jTextNome.setEnabled(false);
				jTextTecnico.setEnabled(false);
				jTextCidade.setEnabled(false);
				comboEstados.setEnabled(false);
				botaoGravar.setEnabled(false);
				botaoDeletar.setEnabled(false);
				
				jTextId.setText("");
				jTextNome.setText("");
				jTextTecnico.setText("");
				jTextCidade.setText("");
				comboEstados.setSelectedItem("");
				
				
			}
		});
		
		
		return janelaTime;
		
	}
	/**
	 * Atualiza o ComboBox de estados com os valores das siglas dos estados
	 */
	public static void atualizarComboboxEstados() {
		try {
	        comboEstados.removeAllItems(); // Remove todos os itens do ComboBox
	        for (String estado : Arrays.asList(Time.siglas)) {
	            comboEstados.addItem(estado); // Adiciona cada sigla de estado ao ComboBox
	        }
	        comboEstados.revalidate(); // Atualiza a exibição do ComboBox
	        comboEstados.repaint(); // Redesenha o ComboBox
		} catch (Exception e) {
			System.out.println("Erro ao consultar os estados: " + e.toString());
		}
        
    }
	/**
	 * Atualiza a lista de jogadores na janela de visualização do time
	 * @param time O objeto Time ao qual os jogadores pertencem
	 */
	public static void atualizarListaJogadores(Time time) {
		modelJanelaTime.setRowCount(0);
		try {
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
			List<Jogador> jogadoresDoTime = Jogador.obterListaDeJogadoresDeTime(time);
			for(Jogador j:jogadoresDoTime) {
				Object[] linha = {j.getId(), j.getNome(), formatoData.format(j.getNascimento())};
				modelJanelaTime.addRow(linha);
			}
			
		}catch (Exception e) {
			System.out.println("Erro ao consultar os times: " + e.toString());
		}
	}
}
