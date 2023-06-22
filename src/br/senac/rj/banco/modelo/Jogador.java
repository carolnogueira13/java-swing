package br.senac.rj.banco.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Jogador {
	
	private int id;
	private String nome;
	private Date nascimento;
	
	private Time time;

	
	public Jogador() {
		super();
	}

	public Jogador(int id, String nome, Date nascimento, Time time) {
		this.id = id;
		this.nome = nome;
		this.nascimento = nascimento;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}
	
	public boolean cadastrarJogador(String nome, Date nascimento, Time time) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			String sql = "INSERT INTO jogador (nome, nascimento, id_time) VALUES (?, ?, ?);";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// Define os parâmetros da consulta
			
			ps.setString(1, nome);
			ps.setDate(2,  new java.sql.Date(nascimento.getTime()));
			ps.setInt(3, time.getId());
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feito o cadastro!");
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
			// Define os parâmetros da consulta
			
			ps.setString(1, nome);
			ps.setDate(2,  new java.sql.Date(nascimento.getTime()));
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feito o cadastro!");
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
	
	public boolean consultarJogador(int id) {
		// Define a conexão
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "select j.id, j.nome, j.nascimento, t.id, t.nome, t.tecnico, t.estado, t.cidade from jogador j LEFT JOIN time t ON j.id_time = t.id where j.id=? ";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parâmetros da consulta
			ps.setInt(1, id);
			// Executa a consulta, resultando em um objeto da classe ResultSet
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // Verifica se não está antes do primeiro registro
				System.out.println("Jogador não cadastrado!");
				return false; // Estudante não cadastrado não cadastrada
			} else {
	            // Efetua a leitura do registro da tabela
	            while (rs.next()) {
	                this.id = rs.getInt("j.id");
	                this.nome = rs.getString("j.nome");
	                this.nascimento = rs.getDate("j.nascimento");

	                int idTime = rs.getInt("t.id");
	                if (rs.wasNull()) {
	                    // time é nulo
	                    this.time = null;
	                } else {
	                    // time não é nulo
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
	
	public boolean atualizaJogador(String nome, Date nascimento, Time time) {
		if (!consultarJogador(id))
			return false;
		else {
			Connection conexao = null;
			try {
				// Define a conexão
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "UPDATE jogador set nome=?, nascimento=?, id_time=? where id=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os parâmetros da atualização
				ps.setString(1, nome);
				ps.setDate(2,  new java.sql.Date(nascimento.getTime()));
				ps.setInt(3, time.getId());
				ps.setInt(4, id);
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("Não foi feita a atualização!");
				else
					System.out.println("Atualização realizada!");
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
				// Define a conexão
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "UPDATE jogador set nome=?, nascimento=?, id_time=null where id=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os parâmetros da atualização
				ps.setString(1, nome);
				ps.setDate(2,  new java.sql.Date(nascimento.getTime()));
				ps.setInt(3, id);
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("Não foi feita a atualização!");
				else
					System.out.println("Atualização realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar o cadastro do jogador: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	
	public boolean deletarJogador(int id) {
		// Define a conexão
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "DELETE from jogador where id=?";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os parâmetros da consulta
			ps.setInt(1, id);
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0)
				System.out.println("Não foi feita a exclusão do jogador!");
			else
				System.out.println("Exclusão realizada!");
				return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao deletar o jogador: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	
	

}
