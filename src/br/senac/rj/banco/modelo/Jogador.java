package br.senac.rj.banco.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Classe que representa um jogador
 *
 */

public class Jogador {
	
	private int id; // Indentificador do jogador
	private String nome; // Nome do jogador
	private Date nascimento; // Data de nascimento do jogador
	private Time time; //Time do jogador
	
	/**
	 * Construtor padrão da classe Jogador.
	 */
	
	public Jogador() {
		super();
	}
	 /**
     * Construtor da classe Jogador que recebe os parâmetros iniciais.
     * 
     * @param id O identificador do jogador.
     * @param nome O nome do jogador.
     * @param nascimento A data de nascimento do jogador.
     * @param time O time ao qual o jogador pertence.
     */

	public Jogador(int id, String nome, Date nascimento, Time time) {
		this.id = id;
		this.nome = nome;
		this.nascimento = nascimento;
		this.time = time;
	}
	/**
	 * Obtém o identificador do jogador
	 * @return o identificador do jogador
	 */
	public int getId() {
		return id;
	}
	/**
	 * Define o identificador do jogador
	 * @param id o identificador do jogador a ser definido
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Obtém o nome do jogador
	 * @return o nome do jogador
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Define o nome do jogador
	 * @param nome o nome do jogador a ser definido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	/**
	 * Obtém a data de nascimento do jogador
	 * @return a data de nascimento do jogador
	 */
	public Date getNascimento() {
		return nascimento;
	}
	/**
	 * Define a data de nascimento do jogador
	 * @param nascimento a data de nascimento do jogador a ser definida
	 */
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	/**
	 * Obtém o time ao qual o jogador pertence
	 * @return o time ao qual o jogador pertence
	 */
	public Time getTime() {
		return time;
	}
	/**
	 * Define o time ao qual o jogador pertence
	 * @param time o time ao qual o jogador pertence a ser definido
	 */
	public void setTime(Time time) {
		this.time = time;
	}
	/**
	 * Realiza o cadastro de um jogador com um time específico
	 * @param nome O nome do jogador
	 * @param nascimento A data de nascimento do jogador
	 * @param time O time ao qual o jogador pertence
	 * @return true se o cadastro foi realizado com sucesso, false caso contrário
	 */
	
	public boolean cadastrarJogador(String nome, Date nascimento, Time time) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "INSERT INTO jogador (nome, nascimento, id_time) VALUES (?, ?, ?);";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// Define os par�metros da consulta
			
			ps.setString(1, nome);
			ps.setDate(2,  new java.sql.Date(nascimento.getTime()));
			ps.setInt(3, time.getId());
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("N�o foi feito o cadastro!");
				return false;
			}
			System.out.println("Cadastro realizado!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar o jogador: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	public boolean cadastrarJogador(String nome, Date nascimento) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "INSERT INTO jogador (nome, nascimento) VALUES (?, ?);";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// Define os par�metros da consulta
			
			ps.setString(1, nome);
			ps.setDate(2,  new java.sql.Date(nascimento.getTime()));
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("N�o foi feito o cadastro!");
				return false;
			}
			System.out.println("Cadastro realizado de jogador sem time!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar o jogador: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	/**
	 * Consulta um jogador pelo seu identificador
	 * @param id O identificador do jogador a ser consultado
	 * @return true se o jogador foi encontrado, false caso contrário
	 */
	
	public boolean consultarJogador(int id) {
		// Define a conexao
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "select j.id, j.nome, j.nascimento, t.id, t.nome, t.tecnico, t.estado, t.cidade from jogador j LEFT JOIN time t ON j.id_time = t.id where j.id=? ";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os par�metros da consulta
			ps.setInt(1, id);
			// Executa a consulta, resultando em um objeto da classe ResultSet
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // Verifica se nao esta antes do primeiro registro
				System.out.println("Jogador n�o cadastrado!");
				return false; // Estudante n�o cadastrado n�o cadastrada
			} else {
	            // Efetua a leitura do registro da tabela
	            while (rs.next()) {
	                this.id = rs.getInt("j.id");
	                this.nome = rs.getString("j.nome");
	                this.nascimento = rs.getDate("j.nascimento");

	                int idTime = rs.getInt("t.id");
	                if (rs.wasNull()) {
	                    // time � nulo
	                    this.time = null;
	                } else {
	                    // time n�o � nulo
	                    String nomeTime = rs.getString("t.nome");
	                    String nomeTecnico = rs.getString("t.tecnico");
	                    String estadoTime = rs.getString("t.estado");
	                    String cidadeTime = rs.getString("t.cidade");
	                    Time time = new Time(idTime, nomeTime, nomeTecnico, estadoTime, cidadeTime);
	                    this.time = time;
	                }
	            }
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar o jogador: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	/**
	 * Atualiza os dados de um jogador existente no banco de dados
	 * @param nome  O novo nome do jogador
	 * @param nascimento A nova data de nascimento do jogador
	 * @param time O novo time ao qual o jogador pertence
	 * @return true se a atualização foi realizada com sucesso, false caso contrário
	 */
	public boolean atualizaJogador(String nome, Date nascimento, Time time) {
		if (!consultarJogador(id))
			return false;
		else {
			Connection conexao = null;
			try {
				// Define a conex�o
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "UPDATE jogador set nome=?, nascimento=?, id_time=? where id=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os par�metros da atualiza��o
				ps.setString(1, nome);
				ps.setDate(2,  new java.sql.Date(nascimento.getTime()));
				ps.setInt(3, time.getId());
				ps.setInt(4, id);
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("N�o foi feita a atualiza��o!");
				else
					System.out.println("Atualiza��o realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar o cadastro do jogador: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	
	public boolean atualizaJogador(String nome, Date nascimento) {
		if (!consultarJogador(id))
			return false;
		else {
			Connection conexao = null;
			try {
				// Define a conex�o
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "UPDATE jogador set nome=?, nascimento=?, id_time=null where id=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os par�metros da atualiza��o
				ps.setString(1, nome);
				ps.setDate(2,  new java.sql.Date(nascimento.getTime()));
				ps.setInt(3, id);
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("N�o foi feita a atualiza��o!");
				else
					System.out.println("Atualiza��o realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar o cadastro do jogador: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	/**
	 * Deleta um jogador do banco de dados com base no seu id
	 * @param id O ID do jogador a ser deletado
	 * @return true se a exclusão foi realizada com sucesso, false caso contrário
	 */
	public boolean deletarJogador(int id) {
		// Define a conex�o
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "DELETE from jogador where id=?";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os par�metros da consulta
			ps.setInt(1, id);
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0)
				System.out.println("N�o foi feita a exclus�o do jogador!");
			else
				System.out.println("Exclus�o realizada!");
				return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao deletar o jogador: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	/**
	 * Obtém uma lista de todos os jogadores do banco de dados
	 * @return Uma lista de objetos Jogador contendo todos os jogadores
	 *  cadastrados no banco de dados.
	 *  Retorna null se não houver jogadores cadastrados
	 */
	public static List<Jogador> obterListaJogadoresDoBanco() {
        List<Jogador> jogadores = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = Conexao.conectaBanco();
            String sql = "select j.id, j.nome, j.nascimento, t.id, t.nome, t.tecnico, t.estado, t.cidade from jogador j LEFT JOIN time t ON j.id_time = t.id";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica se h� registros
                System.out.println("N�o h� times cadastrados!");
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
	/**
	 * Obtém uma lista de jogadores de um determinado time
	 * @param time O objeto Time para  obter a lista de jogadores
	 * @return Uma lista de objetos Jogador contendo os jogadores do time especificado.
	 * Retorna null se não houver jogadores para o time informado
	 */
	public static List<Jogador> obterListaDeJogadoresDeTime(Time time){
		List<Jogador> jogadoresDoTime = new ArrayList<>();
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "select j.id, j.nome, j.nascimento, t.id, t.nome, t.tecnico, t.estado, t.cidade from jogador j LEFT JOIN time t ON j.id_time = t.id where id_time=?";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setInt(1, time.getId());
			ResultSet rs = ps.executeQuery();
			if(!rs.isBeforeFirst()) {
				System.out.println("Nao foi encontrado jogadores");
				return null;
			}else {
				while(rs.next()) {

					int id = rs.getInt("j.id");
					String nome = rs.getString("j.nome");
					Date nascimento = rs.getDate("j.nascimento");
					
					int idTime = rs.getInt("t.id");
				    String nomeTime = rs.getString("t.nome");
				    String nomeTecnico = rs.getString("t.tecnico");
				    String EstadoTime = rs.getString("t.estado");
				    String CidadeTime = rs.getString("t.cidade");
				    Time timeDoJogador = new Time(idTime, nomeTime, nomeTecnico, EstadoTime, CidadeTime);
				    
				    Jogador jogador = new Jogador(id, nome, nascimento, timeDoJogador);
				    jogadoresDoTime.add(jogador);
					
				}
				return jogadoresDoTime;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar jogadores: " + e.toString());
            return null;
		}finally {
			Conexao.fechaConexao(conexao);
		}
	}
	

}
