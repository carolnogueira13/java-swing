package br.senac.rj.banco.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a classificacao de determinados times em um campeonato.
 * 
 *
 */
public class Classificacao {
	
	private int id; // Identificador da classificacao
	private Time time; // Time associado a classificacao
	private int pontuacao; //  Pontuacao da classificacao
	private int vitorias; // Quantidade de vitorias da classificacao
	private int derrotas; // Quantidade de derrotas da classificacao
	private int empates; //Quantidade de empates da classificacao
	/**
	 * Construtor padrao da classe Classificacao
	 */
	public Classificacao() {
		
	}
	/**
	 * 
	 * @param id da classificacao
	 * @param time  que estao associado com a classificacao
	 * @param pontuacao do time na classificacao
	 * @param vitorias o numero de vitorias do time na classificacao
	 * @param derrotas o numero de derrotas do time na classificacao
	 * @param empates o numero de empates do time na classificacao
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
	 * Obtem o id da classificacao
	 * @return o id da classificacao
	 */

	public int getId() {
		return id;
	}
	/**
	 *  Define o ID da classificacao
	 * @param id o ID da classificacao a ser definido
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Obtem o time associado a classificacao
	 * @return o time associado a classificacao
	 */
	public Time getTime() {
		return time;
	}
	/**
	 * Define o time associado a classificacao
	 * @param time o time a ser associado a classificacao
	 */
	public void setTime(Time time) {
		this.time = time;
	}
	/**
	 * Obtem a pontuacao da classificacao
	 * @return a pontuacao da classificacao
	 */
	public int getPontuacao() {
		return pontuacao;
	}
	/**
	 * Define a pontuacao da classificacao
	 * @param pontuacao a pontuacao a ser definida a classificacao
	 */
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	/**
	 * Obtém o numero de vitorias da classificacao
	 * @return o numero de vitorias da classificacao
	 */
	public int getVitorias() {
		return vitorias;
	}

	/**
	 * Define o numero de vitorias da classificacao
	 * @param vitorias o numero de vitorias a ser definido
	 */
	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}
	/**
	 * Obtem o numero de derrotas da classificacao
	 * @return o numero de derrotas da classificacao
	 */
	public int getDerrotas() {
		return derrotas;
	}
	/**
	 * Define o numero de derrotas da classificacao
	 * @param derrotas o numero de derrotas a ser definido
	 */
	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}
	/**
	 * Obtem o numero de empates da classificacao
	 * @return o numero de empates da classificacao
	 */
	public int getEmpates() {
		return empates;
	}
	/**
	 * Define o numero de empates da classificacao
	 * @param empates o numero de empates a ser definido
	 */
	public void setEmpates(int empates) {
		this.empates = empates;
	}
	
	
	/**
	 * Cadastra uma nova classificação de time no banco de dados.
	 *
	 * @param time o time a ser classificado
	 * @param pontuacao a pontuacao do time na classificacao
	 * @param vitorias  o numero de vitorias do time na classificacao
	 * @param derrotas  o numero de derrotas do time na classificacao
	 * @param empates   o numero de empates do time na classificacao
	 * @return true se o cadastro foi realizado com sucesso, false caso contrario
	 */
	public boolean cadastrarClassificacao(Time time, int pontuacao, int vitorias, int derrotas, int empates) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "INSERT INTO classificacao(id_time, pontuacao, vitorias, derrotas, empates) VALUES (?, ?, ?, ?, ?);";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// Define os parametros da consulta
			
			ps.setInt(1, time.getId());
			ps.setInt(2, pontuacao);
			ps.setInt(3, vitorias);
			ps.setInt(4, derrotas);
			ps.setInt(5, empates);
			
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Nao foi feito o cadastro!");
				return false;
			}
			System.out.println("Cadastro realizado!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar a classificacao do time: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	/**
	 * Consulta a classificacao de um time no banco de dados
	 *
	 * @param time o time a ser consultado
	 * @return true se a consulta foi realizada com sucesso, false caso contrario
	 */
	public boolean consultarClassificacao(Time time) {
		// Define a conexao
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "select c.id, c.pontuacao, c.vitorias, c.derrotas, c.empates, t.id, t.nome, t.tecnico, t.estado, t.cidade from classificacao c JOIN time t ON c.id_time = t.id where c.id_time = ? ";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parametros da consulta
			ps.setInt(1, time.getId());
			// Executa a consulta, resultando em um objeto da classe ResultSet
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // Verifica se nao esta antes do primeiro registro
				System.out.println("Classificacao nao cadastrada!");
				return false; // Classificacoa nao cadastrado
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
			System.out.println("Erro ao consultar a classificacao do time: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	/**
	 * Atualiza a classificacao de um time no banco de dados
	 *
	 * @param time o time a ser atualizado
	 * @param pontuacao a nova pontuacao do time na classificacao
	 * @param vitorias o novo numero de vitorias do time na classificacao
	 * @param derrotas o novo numero de derrotas do time na classificacao
	 * @param empates  o novo numero de empates do time na classificacao
	 * @return true se a atualizacao foi realizada com sucesso, false caso contrario
	 */
	public boolean atualizaClassificacao(Time time, int pontuacao, int vitorias, int derrotas, int empates) {
		if (!consultarClassificacao(time))
			return false;
		else {
			Connection conexao = null;
			try {
				// Define a conexao
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "UPDATE classificacao set pontuacao=?, vitorias=?, derrotas=?, empates=? where id_time=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os parametros da atualizacao
				
				ps.setInt(1, pontuacao);
				ps.setInt(2, vitorias);
				ps.setInt(3, derrotas);
				ps.setInt(4, empates);
				ps.setInt(5, time.getId());
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("Nao foi feita a atualizacao!");
				else
					System.out.println("Atualizacao realizada!");
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
	 * Deleta a classificacao de um time no banco de dados
	 *
	 * @param time o time cuja classificacao sera deletada
	 * @return true se a deletacao foi realizada com sucesso, false caso contrario
	 */
	public boolean deletarClassificacao(Time time) {
		// Define a conexao
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "DELETE from classificacao where id_time=?";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parametros da consulta
			ps.setInt(1, time.getId());
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0)
				System.out.println("Nao foi feita a exclusao do time da classificacao!");
			else
				System.out.println("Exclusao realizada!");
				return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao deletar o time da classificacao: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	/**
	 * Obtém a lista de classificacoes dos times a partir do banco de dados
	 * @return A lista de classificacoes dos times
	 */
	public static List<Classificacao> obterListaClassificacaoDoBanco() {
        List<Classificacao> classificacoes = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "select c.id, c.pontuacao, c.vitorias, c.derrotas, c.empates, t.id, t.nome, t.tecnico, t.estado, t.cidade from classificacao c JOIN time t ON t.id = c.id_time ORDER BY c.pontuacao DESC";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica se ha registros
                System.out.println("Nao ha times cadastrados!");
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
            System.out.println("Erro ao consultar classificacoes dos times: " + erro.toString());
            return null;
        } finally {
            Conexao.fechaConexao(conexao);
        }
    }
	
	
	

}
