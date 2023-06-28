package br.senac.rj.banco.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a classificação de determinados times em um campeonato.
 * 
 *
 */
public class Classificacao {
	
	private int id; // Identificador da classificação
	private Time time; // Time associado á classificação
	private int pontuacao; //  Pontuação da classificação
	private int vitorias; // Quantidade de vitórias da classificação
	private int derrotas; // Quantidade de derrotas da classificação
	private int empates; //Quantidade de empates da classificação
	/**
	 * Construtor padrão da classe Classificação
	 */
	public Classificacao() {
		
	}
	/**
	 * 
	 * @param id da classificação
	 * @param time  que está associado com a classificação
	 * @param pontuacao do time na classificação
	 * @param vitorias o número de vitórias do time na classificação
	 * @param derrotas o número de derrotas do time na classificação
	 * @param empates o número de empates do time na classificação
	 */
	public Classificacao(int id, Time time, int pontuacao, int vitorias, int derrotas, int empates) {
		this.id = id;
		this.time = time;
		this.pontuacao = pontuacao;
		this.vitorias = vitorias;
		this.derrotas = derrotas;
		this.empates = empates;
	}
	/**
	 * Obtém o id da classificação
	 * @return o id da classificação
	 */

	public int getId() {
		return id;
	}
	/**
	 *  Define o ID da classificação
	 * @param id o ID da classificação a ser definido
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Obtém o time associado à classificação
	 * @return o time associado à classificação
	 */
	public Time getTime() {
		return time;
	}
	/**
	 * Define o time associado à classificação
	 * @param time o time a ser associado à classificação
	 */
	public void setTime(Time time) {
		this.time = time;
	}
	/**
	 * Obtém a pontuação da classificação
	 * @return a pontuação da classificação
	 */
	public int getPontuacao() {
		return pontuacao;
	}
	/**
	 * Define a pontuação da classificação
	 * @param pontuacao a pontuação a ser definida
	 */
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	/**
	 * Obtém o número de vitórias da classificação
	 * @return o número de vitórias da classificação
	 */
	public int getVitorias() {
		return vitorias;
	}

	/**
	 * Define o número de vitórias da classificação
	 * @param vitorias o número de vitórias a ser definido
	 */
	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}
	/**
	 * Obtém o número de derrotas da classificação
	 * @return o número de derrotas da classificação
	 */
	public int getDerrotas() {
		return derrotas;
	}
	/**
	 * Define o número de derrotas da classificação
	 * @param derrotas o número de derrotas a ser definido
	 */
	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}
	/**
	 * Obtém o número de empates da classificação
	 * @return o número de empates da classificação
	 */
	public int getEmpates() {
		return empates;
	}
	/**
	 * Define o número de empates da classificação
	 * @param empates o número de empates a ser definido
	 */
	public void setEmpates(int empates) {
		this.empates = empates;
	}
	
	
	/**
	 * Cadastra uma nova classificação de time no banco de dados.
	 *
	 * @param time o time a ser classificado
	 * @param pontuacao a pontuação do time na classificação
	 * @param vitorias  o número de vitórias do time na classificação
	 * @param derrotas  o número de derrotas do time na classificação
	 * @param empates   o número de empates do time na classificação
	 * @return true se o cadastro foi realizado com sucesso, false caso contrário
	 */
	public boolean cadastrarClassificacao(Time time, int pontuacao, int vitorias, int derrotas, int empates) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "INSERT INTO classificacao(id_time, pontuacao, vitorias, derrotas, empates) VALUES (?, ?, ?, ?, ?);";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// Define os par�metros da consulta
			
			ps.setInt(1, time.getId());
			ps.setInt(2, pontuacao);
			ps.setInt(3, vitorias);
			ps.setInt(4, derrotas);
			ps.setInt(5, empates);
			
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("N�o foi feito o cadastro!");
				return false;
			}
			System.out.println("Cadastro realizado!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar a classifica��o do time: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	/**
	 * Consulta a classificação de um time no banco de dados
	 *
	 * @param time o time a ser consultado
	 * @return true se a consulta foi realizada com sucesso, false caso contrário
	 */
	public boolean consultarClassificacao(Time time) {
		// Define a conex�o
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "select c.id, c.pontuacao, c.vitorias, c.derrotas, c.empates, t.id, t.nome, t.tecnico, t.estado, t.cidade from classificacao c JOIN time t ON c.id_time = t.id where c.id_time = ? ";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os par�metros da consulta
			ps.setInt(1, time.getId());
			// Executa a consulta, resultando em um objeto da classe ResultSet
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // Verifica se n�o est� antes do primeiro registro
				System.out.println("Classifica��o n�o cadastrada!");
				return false; // Estudante n�o cadastrado n�o cadastrada
			} else {
				// Efetua a leitura do registro da tabela
				while (rs.next()) {
					this.id = rs.getInt("c.id");
					this.pontuacao = rs.getInt("c.pontuacao");
					this.derrotas = rs.getInt("c.derrotas");
					this.vitorias = rs.getInt("c.vitorias");
					this.empates = rs.getInt("c.empates");
					
					int idTime = rs.getInt("t.id");
				    String nomeTime = rs.getString("t.nome");
				    String nomeTecnico = rs.getString("t.tecnico");
				    String EstadoTime = rs.getString("t.estado");
				    String CidadeTime = rs.getString("t.cidade");
				    time = new Time(idTime, nomeTime, nomeTecnico, EstadoTime, CidadeTime);
				    this.time = time;
					
				}
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar a classifica��o do time: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	/**
	 * Atualiza a classificação de um time no banco de dados
	 *
	 * @param time o time a ser atualizado
	 * @param pontuacao a nova pontuação do time na classificação
	 * @param vitorias o novo número de vitórias do time na classificação
	 * @param derrotas o novo número de derrotas do time na classificação
	 * @param empates  o novo número de empates do time na classificação
	 * @return true se a atualização foi realizada com sucesso, false caso contrário
	 */
	public boolean atualizaClassificacao(Time time, int pontuacao, int vitorias, int derrotas, int empates) {
		if (!consultarClassificacao(time))
			return false;
		else {
			Connection conexao = null;
			try {
				// Define a conex�o
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "UPDATE classificacao set pontuacao=?, vitorias=?, derrotas=?, empates=? where id_time=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os par�metros da atualiza��o
				
				ps.setInt(1, pontuacao);
				ps.setInt(2, vitorias);
				ps.setInt(3, derrotas);
				ps.setInt(4, empates);
				ps.setInt(5, time.getId());
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("N�o foi feita a atualiza��o!");
				else
					System.out.println("Atualiza��o realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar o cadastro da classificacao: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	/**
	 * Deleta a classificação de um time no banco de dados
	 *
	 * @param time o time cuja classificação será deletada
	 * @return true se a deletação foi realizada com sucesso, false caso contrário
	 */
	public boolean deletarClassificacao(Time time) {
		// Define a conex�o
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "DELETE from classificacao where id_time=?";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os par�metros da consulta
			ps.setInt(1, time.getId());
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0)
				System.out.println("N�o foi feita a exclus�o do time da classifica��o!");
			else
				System.out.println("Exclus�o realizada!");
				return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao deletar o time da classifica��o: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	/**
	 * Obtém a lista de classificações dos times a partir do banco de dados
	 * @return A lista de classificações dos times
	 */
	public static List<Classificacao> obterListaClassificacaoDoBanco() {
        List<Classificacao> classificacoes = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "select c.id, c.pontuacao, c.vitorias, c.derrotas, c.empates, t.id, t.nome, t.tecnico, t.estado, t.cidade from classificacao c JOIN time t ON t.id = c.id_time ORDER BY c.pontuacao DESC";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica se h� registros
                System.out.println("N�o h� times cadastrados!");
                return null;
            } else {
                // Efetua a leitura dos registros da tabela
                while (rs.next()) {
                	int id = rs.getInt("c.id");
                	int pontuacao = rs.getInt("c.pontuacao");
                	int vitorias = rs.getInt("c.vitorias");
                	int derrotas = rs.getInt("c.derrotas");
                	int empates = rs.getInt("c.empates");
                	
                	
                	int idTime = rs.getInt("t.id");
				    String nomeTime = rs.getString("t.nome");
				    String nomeTecnico = rs.getString("t.tecnico");
				    String EstadoTime = rs.getString("t.estado");
				    String CidadeTime = rs.getString("t.cidade");
				    Time time = new Time(idTime, nomeTime, nomeTecnico, EstadoTime, CidadeTime);
				    
				    Classificacao classif = new Classificacao(id, time, pontuacao, vitorias,derrotas,empates);
				    classificacoes.add(classif);
                    
                }
                return classificacoes;
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao consultar classifica��es dos times: " + erro.toString());
            return null;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
	
	
	

}
